import React, { useEffect, useState } from 'react';
import api from '../helper/axios';
import { 
  LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer 
} from 'recharts';
import {
  Container,
  Box,
  Typography,
  Grid,
  Card,
  CardContent,
  TextField,
  Button,
  Divider,
  Paper,
  IconButton,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import CloseIcon from '@mui/icons-material/Close';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import DeleteIcon from '@mui/icons-material/Delete';
import { useNavigate } from 'react-router-dom';

interface WeightLog {
  id: number;
  date: string;
  weight: number;
}

interface UserProfile {
  weight: number;
  height: number;
  age: number;
  gender: string;
  waist: number;
  hip: number;
  thigh: number;
  arm: number;
}

interface BodyFatLog {
  id: number;
  date: string;
  value: number;
}

const HealthDashboard: React.FC = () => {
  const [history, setHistory] = useState<WeightLog[]>([]);
  const [bfHistory, setBfHistory] = useState<BodyFatLog[]>([]);
  const [profile, setProfile] = useState<UserProfile>({ 
    weight: 0, height: 0, age: 0, gender: '', waist: 0, hip: 0, thigh: 0, arm: 0 
  });
  const [newWeight, setNewWeight] = useState('');
  const [newHeight, setNewHeight] = useState('');
  const [newAge, setNewAge] = useState('');
  const [newGender, setNewGender] = useState('');
  const [newWaist, setNewWaist] = useState('');
  const [newHip, setNewHip] = useState('');
  const [newThigh, setNewThigh] = useState('');
  const [newArm, setNewArm] = useState('');
  const [isEditing, setIsEditing] = useState(false);
  const [isPredicting, setIsPredicting] = useState(false);
  
  const navigate = useNavigate();
  const userId = localStorage.getItem('USER_ID');

  const fetchData = async () => {
    try {
      const [historyRes, bfHistoryRes, userRes] = await Promise.all([
        api.get(`/api/weight-logs/user/${userId}`),
        api.get(`/api/bodyfat/history`),
        api.get(`/api/users/${userId}`)
      ]);
      setHistory(historyRes.data);
      setBfHistory(bfHistoryRes.data);
      setProfile({
        weight: userRes.data.weight || 0,
        height: userRes.data.height || 0,
        age: userRes.data.age || 0,
        gender: userRes.data.gender || '',
        waist: userRes.data.waist || 0,
        hip: userRes.data.hip || 0,
        thigh: userRes.data.thigh || 0,
        arm: userRes.data.arm || 0
      });
      setNewHeight(userRes.data.height?.toString() || '');
      setNewAge(userRes.data.age?.toString() || '');
      setNewGender(userRes.data.gender || '');
      setNewWaist(userRes.data.waist?.toString() || '');
      setNewHip(userRes.data.hip?.toString() || '');
      setNewThigh(userRes.data.thigh?.toString() || '');
      setNewArm(userRes.data.arm?.toString() || '');
    } catch (err) {
      console.error("Error fetching health data", err);
    }
  };

  useEffect(() => { 
    if (userId) fetchData(); 
    else navigate('/login');
  }, [userId]);

  const handleWeightSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newWeight) return;
    const weightVal = parseFloat(newWeight);
    if (weightVal > 500) {
      alert("Weight cannot exceed 500 kg.");
      return;
    }
    try {
      await api.post(`/api/weight-logs`, null, { params: { weight: newWeight } });
      await api.put(`/api/users/${userId}/profile`, { 
        ...profile, 
        weight: parseFloat(newWeight) 
      });
      setNewWeight('');
      fetchData();
    } catch (err) {
      alert("Failed to log weight");
    }
  };

  const handleProfileUpdate = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await api.put(`/api/users/${userId}/profile`, {
        weight: profile.weight,
        height: parseFloat(newHeight),
        age: parseInt(newAge),
        gender: newGender,
        waist: parseFloat(newWaist),
        hip: parseFloat(newHip),
        thigh: parseFloat(newThigh),
        arm: parseFloat(newArm)
      });
      setIsEditing(false);
      fetchData();
    } catch (err) {
      alert("Failed to update profile");
    }
  };

  const handleDeleteLog = async (id: number) => {
    if (!window.confirm("Are you sure you want to delete this log?")) return;
    try {
      await api.delete(`/api/weight-logs/${id}`);
      fetchData();
    } catch (err) {
      alert("Failed to delete log");
    }
  };

  const handleDeleteBfLog = async (id: number) => {
    if (!window.confirm("Are you sure you want to delete this body fat log?")) return;
    try {
      await api.delete(`/api/bodyfat/${id}`);
      fetchData();
    } catch (err) {
      alert("Failed to delete body fat log");
    }
  };

  const handlePredictBodyFat = async () => {
    if (!profile.gender || !profile.waist || !profile.weight || !profile.height) {
      alert("Please ensure your profile has Gender, Weight, Height, and Waist measurement.");
      setIsEditing(true);
      return;
    }
    setIsPredicting(true);
    try {
      await api.post(`/api/bodyfat/predict`);
      fetchData();
      alert("Body fat prediction completed!");
    } catch (err: any) {
      alert("Failed to predict body fat: " + (err.response?.data?.message || err.message));
    } finally {
      setIsPredicting(false);
    }
  };

  const calculateBMI = () => {
    if (!profile.height || !profile.weight) return "N/A";
    const heightInMeters = profile.height / 100;
    return (profile.weight / (heightInMeters * heightInMeters)).toFixed(1);
  };

  const getBMICategory = (bmi: string) => {
    const val = parseFloat(bmi);
    if (isNaN(val)) return { label: 'N/A', color: 'textSecondary' };
    if (val < 18.5) return { label: 'Underweight', color: 'warning.main' };
    if (val < 25) return { label: 'Healthy Weight', color: 'success.main' };
    if (val < 30) return { label: 'Overweight', color: 'warning.main' };
    return { label: 'Obese', color: 'error.main' };
  };

  const bmiInfo = getBMICategory(calculateBMI());

  return (
    <Container maxWidth="lg" sx={{ py: 4 }}>
      <Box display="flex" alignItems="center" mb={4}>
        <IconButton onClick={() => navigate('/dashboard')} sx={{ mr: 2 }}>
          <ArrowBackIcon />
        </IconButton>
        <Typography variant="h3" fontWeight="bold" color="primary">
          Health Dashboard
        </Typography>
        <Box flexGrow={1} />
        <button 
          onClick={() => setIsEditing(!isEditing)}
          style={{ 
            background: 'none', border: 'none', color: isEditing ? '#e53935' : '#1976d2', 
            cursor: 'pointer', fontWeight: 'bold', display: 'flex', alignItems: 'center', gap: '4px' 
          }}
        >
          {isEditing ? <><CloseIcon fontSize="small"/> Cancel</> : <><EditIcon fontSize="small"/> Edit Profile</>}
        </button>
      </Box>

      <Grid container spacing={4}>
        {/* Stats Section */}
        <Grid size={{ xs: 12, md: 4 }}>
          <Card elevation={3} sx={{ height: '100%', borderRadius: 3 }}>
            <CardContent>
              <Typography variant="overline" color="textSecondary" fontWeight="bold">Current Weight</Typography>
              <Box display="flex" alignItems="baseline">
                <Typography variant="h2" fontWeight="900" color="primary">{profile.weight}</Typography>
                <Typography variant="h6" color="textSecondary" sx={{ ml: 1 }}>kg</Typography>
              </Box>
              
              <Box component="form" onSubmit={handleWeightSubmit} mt={2} display="flex" gap={1}>
                <TextField 
                  size="small" 
                  label="Log Weight" 
                  type="number" 
                  value={newWeight} 
                  onChange={(e) => setNewWeight(e.target.value)} 
                  sx={{ flexGrow: 1 }}
                />
                <Button variant="contained" type="submit" size="small">Log</Button>
              </Box>

              <Divider sx={{ my: 2 }} />
              <Typography variant="body2" color="textSecondary">
                Height: <strong>{profile.height} cm</strong> | Age: <strong>{profile.age}</strong>
              </Typography>
            </CardContent>
          </Card>
        </Grid>

        <Grid size={{ xs: 12, md: 4 }}>
          <Card elevation={3} sx={{ height: '100%', borderRadius: 3 }}>
            <CardContent>
              <Typography variant="overline" color="textSecondary" fontWeight="bold">Latest Body Fat</Typography>
              <Box display="flex" alignItems="baseline">
                <Typography variant="h2" fontWeight="900" color="secondary.main">
                  {bfHistory.length > 0 ? bfHistory[0].value.toFixed(1) : 'N/A'}
                </Typography>
                <Typography variant="h6" color="textSecondary" sx={{ ml: 1 }}>%</Typography>
              </Box>
              <Divider sx={{ my: 2 }} />
              <Button 
                variant="contained" 
                color="secondary" 
                fullWidth 
                onClick={handlePredictBodyFat}
                disabled={isPredicting}
              >
                {isPredicting ? "Predicting..." : "Predict Body Fat"}
              </Button>
            </CardContent>
          </Card>
        </Grid>

        <Grid size={{ xs: 12, md: 4 }}>
          <Card elevation={3} sx={{ height: '100%', borderRadius: 3 }}>
            <CardContent>
              <Typography variant="overline" color="textSecondary" fontWeight="bold">BMI Score</Typography>
              <Typography variant="h2" fontWeight="900" color="success.main">{calculateBMI()}</Typography>
              <Divider sx={{ my: 2 }} />
              <Typography variant="body1" fontWeight="bold" sx={{ color: bmiInfo.color }}>{bmiInfo.label}</Typography>
            </CardContent>
          </Card>
        </Grid>

        {isEditing && (
          <Grid size={{ xs: 12 }}>
            <Paper sx={{ p: 3, borderRadius: 3, bgcolor: '#e3f2fd' }}>
              <Typography variant="h6" gutterBottom fontWeight="bold">Update Physical Profile</Typography>
              <form onSubmit={handleProfileUpdate}>
                <Grid container spacing={2}>
                  <Grid size={{ xs: 12, sm: 3 }}>
                    <TextField label="Height (cm)" type="number" value={newHeight} onChange={(e) => setNewHeight(e.target.value)} fullWidth sx={{ bgcolor: 'white' }} />
                  </Grid>
                  <Grid size={{ xs: 12, sm: 3 }}>
                    <TextField label="Age" type="number" value={newAge} onChange={(e) => setNewAge(e.target.value)} fullWidth sx={{ bgcolor: 'white' }} />
                  </Grid>
                  <Grid size={{ xs: 12, sm: 3 }}>
                    <TextField 
                      select 
                      label="Gender" 
                      value={newGender} 
                      onChange={(e) => setNewGender(e.target.value)} 
                      fullWidth 
                      sx={{ bgcolor: 'white' }}
                      SelectProps={{ native: true }}
                    >
                      <option value=""></option>
                      <option value="MALE">Male</option>
                      <option value="FEMALE">Female</option>
                    </TextField>
                  </Grid>
                  <Grid size={{ xs: 12, sm: 3 }}>
                    <TextField label="Waist (cm)" type="number" value={newWaist} onChange={(e) => setNewWaist(e.target.value)} fullWidth sx={{ bgcolor: 'white' }} />
                  </Grid>
                  <Grid size={{ xs: 12, sm: 3 }}>
                    <TextField label="Hip (cm)" type="number" value={newHip} onChange={(e) => setNewHip(e.target.value)} fullWidth sx={{ bgcolor: 'white' }} />
                  </Grid>
                  <Grid size={{ xs: 12, sm: 3 }}>
                    <TextField label="Thigh (cm)" type="number" value={newThigh} onChange={(e) => setNewThigh(e.target.value)} fullWidth sx={{ bgcolor: 'white' }} />
                  </Grid>
                  <Grid size={{ xs: 12, sm: 3 }}>
                    <TextField label="Arm (cm)" type="number" value={newArm} onChange={(e) => setNewArm(e.target.value)} fullWidth sx={{ bgcolor: 'white' }} />
                  </Grid>
                  <Grid size={{ xs: 12, sm: 3 }}>
                    <Button type="submit" variant="contained" color="primary" fullWidth sx={{ height: 56 }}>Save Profile</Button>
                  </Grid>
                </Grid>
              </form>
            </Paper>
          </Grid>
        )}

        <Grid size={{ xs: 12, md: 6 }}>
          <Paper elevation={3} sx={{ p: 4, borderRadius: 3 }}>
            <Typography variant="h5" fontWeight="bold" gutterBottom>Weight Journey</Typography>
            <Box sx={{ width: '100%', height: 300, mt: 4 }}>
              <ResponsiveContainer width="100%" height="100%">
                <LineChart data={history}>
                  <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#f0f0f0" />
                  <XAxis dataKey="date" tick={{fontSize: 12}} axisLine={false} tickLine={false} dy={10} tickFormatter={(str) => new Date(str).toLocaleDateString(undefined, { month: 'short', day: 'numeric' })} />
                  <YAxis domain={['dataMin - 2', 'dataMax + 2']} tick={{fontSize: 12}} axisLine={false} tickLine={false} />
                  <Tooltip contentStyle={{ borderRadius: '12px', border: 'none', boxShadow: '0 4px 12px rgba(0,0,0,0.1)' }} />
                  <Line type="monotone" dataKey="weight" stroke="#1976d2" strokeWidth={4} dot={{ r: 4 }} />
                </LineChart>
              </ResponsiveContainer>
            </Box>
          </Paper>
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <Paper elevation={3} sx={{ p: 4, borderRadius: 3 }}>
            <Typography variant="h5" fontWeight="bold" gutterBottom>Body Fat Progress</Typography>
            <Box sx={{ width: '100%', height: 300, mt: 4 }}>
              <ResponsiveContainer width="100%" height="100%">
                <LineChart data={[...bfHistory].reverse()}>
                  <CartesianGrid strokeDasharray="3 3" vertical={false} stroke="#f0f0f0" />
                  <XAxis dataKey="date" tick={{fontSize: 12}} axisLine={false} tickLine={false} dy={10} tickFormatter={(str) => new Date(str).toLocaleDateString(undefined, { month: 'short', day: 'numeric' })} />
                  <YAxis domain={['dataMin - 2', 'dataMax + 2']} tick={{fontSize: 12}} axisLine={false} tickLine={false} />
                  <Tooltip contentStyle={{ borderRadius: '12px', border: 'none', boxShadow: '0 4px 12px rgba(0,0,0,0.1)' }} />
                  <Line type="monotone" dataKey="value" stroke="#9c27b0" strokeWidth={4} dot={{ r: 4 }} />
                </LineChart>
              </ResponsiveContainer>
            </Box>
          </Paper>
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TableContainer component={Paper} elevation={3} sx={{ borderRadius: 3 }}>
            <Box p={3} pb={2}><Typography variant="h6" fontWeight="bold">Weight Logs</Typography></Box>
            <Table>
              <TableHead><TableRow><TableCell>Date</TableCell><TableCell>Weight</TableCell><TableCell align="right">Action</TableCell></TableRow></TableHead>
              <TableBody>
                {[...history].reverse().slice(0, 5).map((log) => (
                  <TableRow key={log.id}>
                    <TableCell>{new Date(log.date).toLocaleDateString()}</TableCell>
                    <TableCell><strong>{log.weight} kg</strong></TableCell>
                    <TableCell align="right">
                      <IconButton color="error" size="small" onClick={() => handleDeleteLog(log.id)}><DeleteIcon fontSize="small"/></IconButton>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Grid>

        <Grid size={{ xs: 12, md: 6 }}>
          <TableContainer component={Paper} elevation={3} sx={{ borderRadius: 3 }}>
            <Box p={3} pb={2}><Typography variant="h6" fontWeight="bold">Body Fat Logs</Typography></Box>
            <Table>
              <TableHead><TableRow><TableCell>Date</TableCell><TableCell>Body Fat</TableCell><TableCell align="right">Action</TableCell></TableRow></TableHead>
              <TableBody>
                {bfHistory.slice(0, 5).map((log) => (
                  <TableRow key={log.id}>
                    <TableCell>{new Date(log.date).toLocaleDateString()}</TableCell>
                    <TableCell><strong>{log.value.toFixed(1)} %</strong></TableCell>
                    <TableCell align="right">
                      <IconButton color="error" size="small" onClick={() => handleDeleteBfLog(log.id)}><DeleteIcon fontSize="small"/></IconButton>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Grid>
      </Grid>
    </Container>
  );
};

export default HealthDashboard;
