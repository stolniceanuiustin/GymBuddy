import React, { useEffect, useState } from 'react';
import { 
    Container, Typography, Paper, Table, TableBody, TableCell, TableContainer, 
    TableHead, TableRow, Box, Button, CircularProgress, Alert, Chip,
    Dialog, DialogTitle, DialogContent, DialogActions, TextField, 
    FormControlLabel, Checkbox, Stack
} from '@mui/material';
import axios from '../helper/axios';
import { useNavigate } from 'react-router-dom';

interface ExerciseType {
    id?: number;
    name: string;
    description: string;
    isBodyweight: boolean;
}

const AdminExercises: React.FC = () => {
    const [exercises, setExercises] = useState<ExerciseType[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [open, setOpen] = useState(false);
    const [editingExercise, setEditingExercise] = useState<ExerciseType | null>(null);
    
    const [formData, setFormData] = useState<ExerciseType>({
        name: '',
        description: '',
        isBodyweight: false
    });

    const navigate = useNavigate();

    const fetchExercises = () => {
        setLoading(true);
        axios.get('/api/exercise-types')
            .then(response => {
                setExercises(response.data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Fetch Exercises Error:", err);
                setError("Failed to fetch exercise catalog.");
                setLoading(false);
            });
    };

    useEffect(() => {
        fetchExercises();
    }, []);

    const handleOpen = (exercise?: ExerciseType) => {
        if (exercise) {
            setEditingExercise(exercise);
            setFormData(exercise);
        } else {
            setEditingExercise(null);
            setFormData({
                name: '',
                description: '',
                isBodyweight: false
            });
        }
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
        setError(null);
    };

    const handleSubmit = async () => {
        try {
            if (editingExercise) {
                await axios.put(`/api/exercise-types/${editingExercise.id}`, formData);
            } else {
                await axios.post('/api/exercise-types', formData);
            }
            fetchExercises();
            handleClose();
        } catch (err: any) {
            setError(err.response?.data || "Failed to save exercise.");
        }
    };

    const handleDelete = async (id: number) => {
        if (window.confirm("Are you sure you want to delete this exercise from the catalog?")) {
            try {
                await axios.delete(`/api/exercise-types/${id}`);
                fetchExercises();
            } catch (err: any) {
                setError("Failed to delete exercise. It might be in use by users.");
            }
        }
    };

    if (loading && exercises.length === 0) return (
        <Box display="flex" justifyContent="center" mt={10}>
            <CircularProgress />
        </Box>
    );

    return (
        <Container maxWidth="lg" sx={{ mt: 5 }}>
            <Box display="flex" justifyContent="space-between" alignItems="center" mb={4}>
                <Typography variant="h4" fontWeight="bold">Exercise Catalog</Typography>
                <Stack direction="row" spacing={2}>
                    <Button variant="contained" color="primary" onClick={() => handleOpen()}>Add New Exercise</Button>
                    <Button variant="outlined" onClick={() => navigate('/dashboard')}>Back</Button>
                </Stack>
            </Box>

            {error && <Alert severity="error" sx={{ mb: 3 }}>{error}</Alert>}

            <TableContainer component={Paper} elevation={3}>
                <Table>
                    <TableHead sx={{ backgroundColor: '#f5f5f5' }}>
                        <TableRow>
                            <TableCell><strong>ID</strong></TableCell>
                            <TableCell><strong>Name</strong></TableCell>
                            <TableCell><strong>Description</strong></TableCell>
                            <TableCell><strong>Type</strong></TableCell>
                            <TableCell><strong>Actions</strong></TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {exercises.map((ex) => (
                            <TableRow key={ex.id} hover>
                                <TableCell>{ex.id}</TableCell>
                                <TableCell>{ex.name}</TableCell>
                                <TableCell>{ex.description}</TableCell>
                                <TableCell>
                                    <Chip 
                                        label={ex.isBodyweight ? "Bodyweight" : "With Weights"} 
                                        color={ex.isBodyweight ? "success" : "secondary"}
                                        variant="outlined"
                                        size="small"
                                    />
                                </TableCell>
                                <TableCell>
                                    <Button size="small" onClick={() => handleOpen(ex)}>Edit</Button>
                                    <Button size="small" color="error" onClick={() => handleDelete(ex.id!)}>Delete</Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            {/* Add/Edit Dialog */}
            <Dialog open={open} onClose={handleClose} fullWidth maxWidth="sm">
                <DialogTitle>{editingExercise ? 'Edit Exercise' : 'Add New Exercise'}</DialogTitle>
                <DialogContent>
                    <Box component="form" sx={{ mt: 2 }}>
                        <TextField
                            fullWidth label="Exercise Name" margin="normal"
                            value={formData.name}
                            onChange={(e) => setFormData({...formData, name: e.target.value})}
                        />
                        <TextField
                            fullWidth label="Description" margin="normal" multiline rows={2}
                            value={formData.description}
                            onChange={(e) => setFormData({...formData, description: e.target.value})}
                        />
                        <FormControlLabel
                            control={
                                <Checkbox 
                                    checked={formData.isBodyweight} 
                                    onChange={(e) => setFormData({...formData, isBodyweight: e.target.checked})} 
                                />
                            }
                            label="Is this a Bodyweight exercise?"
                        />
                    </Box>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={handleSubmit} variant="contained">Save</Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
};

export default AdminExercises;
