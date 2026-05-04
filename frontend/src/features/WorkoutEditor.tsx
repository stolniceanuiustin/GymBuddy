import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import {
    Container,
    Box,
    Typography,
    TextField,
    Button,
    Card,
    CardContent,
    Grid,
    MenuItem,
    IconButton,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Divider,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import SaveIcon from '@mui/icons-material/Save';
import axios from '../helper/axios';

interface ExerciseSetDTO {
    id?: number;
    setNumber: number;
    reps: number;
    weight: number | null;
}

interface ExerciseTypeDTO {
    id: number;
    name: string;
    description: string;
    bodyweight: boolean;
}

interface ExerciseDTO {
    id?: number;
    exerciseType: ExerciseTypeDTO;
    sets: ExerciseSetDTO[];
}

interface GymDayDTO {
    id?: number;
    name: string;
    date: string;
    notes: string;
    sleepQuality: number;
    energyLevel: number;
    userId: number;
    exercises: ExerciseDTO[];
}

const WorkoutEditor: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
    const userId = Number(localStorage.getItem('USER_ID'));

    const [workout, setWorkout] = useState<GymDayDTO>({
        name: '',
        date: new Date().toISOString().split('T')[0],
        notes: '',
        sleepQuality: 5,
        energyLevel: 5,
        userId: userId,
        exercises: []
    });

    const [catalog, setCatalog] = useState<ExerciseTypeDTO[]>([]);
    const [selectedTypeId, setSelectedTypeId] = useState<string>('');

    useEffect(() => {
        axios.get('/api/exercise-types').then(res => setCatalog(res.data));

        if (id) {
            axios.get(`/api/gymdays/${id}`).then(res => setWorkout(res.data));
        }
    }, [id]);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setWorkout({ ...workout, [name]: name === 'sleepQuality' || name === 'energyLevel' ? Number(value) : value });
    };

    const addExercise = () => {
        if (!selectedTypeId) return;
        const type = catalog.find(t => t.id === Number(selectedTypeId));
        if (!type) return;

        const newExercise: ExerciseDTO = {
            exerciseType: type,
            sets: []
        };
        setWorkout({ ...workout, exercises: [...workout.exercises, newExercise] });
        setSelectedTypeId('');
    };

    const removeExercise = (index: number) => {
        const updatedExercises = workout.exercises.filter((_, i) => i !== index);
        setWorkout({ ...workout, exercises: updatedExercises });
    };

    const addSet = (exerciseIndex: number) => {
        const updatedExercises = [...workout.exercises];
        const exercise = updatedExercises[exerciseIndex];
        const newSet: ExerciseSetDTO = {
            setNumber: exercise.sets.length + 1,
            reps: 0,
            weight: exercise.exerciseType.bodyweight ? null : 0
        };
        exercise.sets.push(newSet);
        setWorkout({ ...workout, exercises: updatedExercises });
    };

    const handleSetChange = (exerciseIndex: number, setIndex: number, field: string, value: string) => {
        const updatedExercises = [...workout.exercises];
        const set = updatedExercises[exerciseIndex].sets[setIndex];
        if (field === 'reps') set.reps = Number(value);
        if (field === 'weight') set.weight = value === '' ? null : Number(value);
        setWorkout({ ...workout, exercises: updatedExercises });
    };

    const removeSet = (exerciseIndex: number, setIndex: number) => {
        const updatedExercises = [...workout.exercises];
        updatedExercises[exerciseIndex].sets.splice(setIndex, 1);
        updatedExercises[exerciseIndex].sets.forEach((s, i) => s.setNumber = i + 1);
        setWorkout({ ...workout, exercises: updatedExercises });
    };

    const saveWorkout = async () => {
        try {
            if (workout.id) {
                await axios.put(`/api/gymdays`, workout);
            } else {
                await axios.post('/api/gymdays', workout);
            }
            navigate('/dashboard');
        } catch (err: any) {
            const errorMsg = err.response?.data?.message || 'Error saving workout. Make sure all fields are valid.';
            alert(errorMsg);
        }
    };

    return (
        <Container maxWidth="md" sx={{ py: 4 }}>
            <Box display="flex" alignItems="center" mb={4}>
                <IconButton onClick={() => navigate('/dashboard')} sx={{ mr: 2 }}>
                    <ArrowBackIcon />
                </IconButton>
                <Typography variant="h4" fontWeight="bold" color="primary">
                    {workout.id ? 'Edit Workout' : 'Log New Workout'}
                </Typography>
            </Box>

            <Card sx={{ mb: 4, borderRadius: 3 }} elevation={3}>
                <CardContent>
                    <Typography variant="h6" gutterBottom fontWeight="bold">General Info</Typography>
                    <Grid container spacing={3}>
                        <Grid size={{ xs: 12, md: 6 }}>
                            <TextField
                                label="Workout Name"
                                name="name"
                                value={workout.name}
                                onChange={handleInputChange}
                                fullWidth
                                placeholder="e.g. Leg Day"
                                required
                            />
                        </Grid>
                        <Grid size={{ xs: 12, md: 6 }}>
                            <TextField
                                label="Date"
                                type="date"
                                name="date"
                                value={workout.date}
                                onChange={handleInputChange}
                                fullWidth
                                InputLabelProps={{ shrink: true }}
                                required
                            />
                        </Grid>
                        <Grid size={{ xs: 12, md: 6 }}>
                            <TextField
                                label="Sleep Quality (1-10)"
                                type="number"
                                name="sleepQuality"
                                value={workout.sleepQuality}
                                onChange={handleInputChange}
                                fullWidth
                                inputProps={{ min: 1, max: 10 }}
                            />
                        </Grid>
                        <Grid size={{ xs: 12, md: 6 }}>
                            <TextField
                                label="Energy Level (1-10)"
                                type="number"
                                name="energyLevel"
                                value={workout.energyLevel}
                                onChange={handleInputChange}
                                fullWidth
                                inputProps={{ min: 1, max: 10 }}
                            />
                        </Grid>
                        <Grid size={{ xs: 12 }}>
                            <TextField
                                label="Notes"
                                name="notes"
                                value={workout.notes}
                                onChange={handleInputChange}
                                fullWidth
                                multiline
                                rows={2}
                            />
                        </Grid>
                    </Grid>
                </CardContent>
            </Card>

            <Box mb={4}>
                <Typography variant="h5" gutterBottom fontWeight="bold" color="primary">Exercises</Typography>
                <Box display="flex" gap={2} mb={3}>
                    <TextField
                        select
                        label="Add Exercise"
                        value={selectedTypeId}
                        onChange={(e) => setSelectedTypeId(e.target.value)}
                        sx={{ minWidth: 250 }}
                    >
                        <MenuItem value=""><em>-- Select --</em></MenuItem>
                        {catalog.map(type => (
                            <MenuItem key={type.id} value={type.id}>{type.name}</MenuItem>
                        ))}
                    </TextField>
                    <Button
                        variant="contained"
                        startIcon={<AddIcon />}
                        onClick={addExercise}
                        disabled={!selectedTypeId}
                    >
                        Add
                    </Button>
                </Box>

                {workout.exercises.map((ex, exIndex) => (
                    <Card key={exIndex} sx={{ mb: 3, borderRadius: 2, border: '1px solid #e0e0e0' }} elevation={1}>
                        <CardContent>
                            <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                                <Typography variant="h6" fontWeight="bold">{ex.exerciseType.name}</Typography>
                                <IconButton color="error" onClick={() => removeExercise(exIndex)}>
                                    <DeleteIcon />
                                </IconButton>
                            </Box>
                            
                            <TableContainer component={Paper} variant="outlined" sx={{ mb: 2 }}>
                                <Table size="small">
                                    <TableHead sx={{ bgcolor: '#f5f5f5' }}>
                                        <TableRow>
                                            <TableCell width="10%">Set</TableCell>
                                            <TableCell width="30%">Reps</TableCell>
                                            {!ex.exerciseType.bodyweight && <TableCell width="30%">Weight (kg)</TableCell>}
                                            <TableCell width="10%" align="right">Action</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {ex.sets.map((set, setIndex) => (
                                            <TableRow key={setIndex}>
                                                <TableCell>{set.setNumber}</TableCell>
                                                <TableCell>
                                                    <TextField
                                                        size="small"
                                                        type="number"
                                                        value={set.reps}
                                                        onChange={(e) => handleSetChange(exIndex, setIndex, 'reps', e.target.value)}
                                                        fullWidth
                                                    />
                                                </TableCell>
                                                {!ex.exerciseType.bodyweight && (
                                                    <TableCell>
                                                        <TextField
                                                            size="small"
                                                            type="number"
                                                            value={set.weight || ''}
                                                            onChange={(e) => handleSetChange(exIndex, setIndex, 'weight', e.target.value)}
                                                            fullWidth
                                                        />
                                                    </TableCell>
                                                )}
                                                <TableCell align="right">
                                                    <IconButton size="small" color="error" onClick={() => removeSet(exIndex, setIndex)}>
                                                        <DeleteIcon fontSize="small" />
                                                    </IconButton>
                                                </TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                            <Button
                                size="small"
                                startIcon={<AddIcon />}
                                onClick={() => addSet(exIndex)}
                                variant="outlined"
                            >
                                Add Set
                            </Button>
                        </CardContent>
                    </Card>
                ))}
            </Box>

            <Divider sx={{ my: 4 }} />

            <Box display="flex" gap={2} justifyContent="center">
                <Button
                    variant="contained"
                    size="large"
                    color="primary"
                    startIcon={<SaveIcon />}
                    onClick={saveWorkout}
                    sx={{ px: 4, py: 1.5 }}
                >
                    Save Workout
                </Button>
                <Button
                    variant="outlined"
                    size="large"
                    onClick={() => navigate('/dashboard')}
                    sx={{ px: 4, py: 1.5 }}
                >
                    Cancel
                </Button>
            </Box>
        </Container>
    );
};

export default WorkoutEditor;
