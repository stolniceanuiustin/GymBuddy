import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from '../helper/axios';

// Interfaces matching the backend DTOs for type safety
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

    // State for the entire workout session
    const [workout, setWorkout] = useState<GymDayDTO>({
        name: '',
        date: new Date().toISOString().split('T')[0],
        notes: '',
        sleepQuality: 5,
        energyLevel: 5,
        userId: userId,
        exercises: []
    });

    // State for available exercise types from catalog
    const [catalog, setCatalog] = useState<ExerciseTypeDTO[]>([]);
    const [selectedTypeId, setSelectedTypeId] = useState<string>('');

    // Fetch existing workout if editing, and fetch catalog
    useEffect(() => {
        axios.get('/api/exercise-types').then(res => setCatalog(res.data));

        if (id) {
            axios.get(`/api/gymdays/${id}`).then(res => setWorkout(res.data));
        }
    }, [id]);

    // Handle simple input changes for the workout metadata
    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setWorkout({ ...workout, [name]: name === 'sleepQuality' || name === 'energyLevel' ? Number(value) : value });
    };

    // Add a new exercise to the workout session
    const addExercise = () => {
        if (!selectedTypeId) return;
        const type = catalog.find(t => t.id === Number(selectedTypeId));
        if (!type) return;

        const newExercise: ExerciseDTO = {
            exerciseType: type,
            sets: []
        };
        setWorkout({ ...workout, exercises: [...workout.exercises, newExercise] });
    };

    // Remove an exercise
    const removeExercise = (index: number) => {
        const updatedExercises = workout.exercises.filter((_, i) => i !== index);
        setWorkout({ ...workout, exercises: updatedExercises });
    };

    // Add a set to a specific exercise
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

    // Update set data
    const handleSetChange = (exerciseIndex: number, setIndex: number, field: string, value: string) => {
        const updatedExercises = [...workout.exercises];
        const set = updatedExercises[exerciseIndex].sets[setIndex];
        if (field === 'reps') set.reps = Number(value);
        if (field === 'weight') set.weight = value === '' ? null : Number(value);
        setWorkout({ ...workout, exercises: updatedExercises });
    };

    // Save the full workout to the backend
    const saveWorkout = async () => {
        try {
            if (workout.id) {
                await axios.put(`/api/gymdays`, workout); // Our GymDayService handles full update via DTO
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
        <div style={{ padding: '20px', maxWidth: '800px', margin: '0 auto' }}>
            <h1>{workout.id ? 'Edit Workout' : 'Log New Workout'}</h1>
            <button onClick={() => navigate('/dashboard')}>Back to Dashboard</button>
            <hr />

            <div>
                <h3>General Info</h3>
                <label>Date: </label>
                <input type="date" name="date" value={workout.date} onChange={handleInputChange} /><br />
                <label>Workout Name: </label>
                <input type="text" name="name" value={workout.name} onChange={handleInputChange} placeholder="e.g. Leg Day" /><br />
                <label>Sleep Quality (1-10): </label>
                <input type="number" name="sleepQuality" min="1" max="10" value={workout.sleepQuality} onChange={handleInputChange} /><br />
                <label>Energy Level (1-10): </label>
                <input type="number" name="energyLevel" min="1" max="10" value={workout.energyLevel} onChange={handleInputChange} /><br />
                <label>Notes: </label><br />
                <textarea name="notes" value={workout.notes} onChange={handleInputChange} style={{ width: '100%' }} />
            </div>

            <hr />
            <div>
                <h3>Exercises</h3>
                <select value={selectedTypeId} onChange={(e) => setSelectedTypeId(e.target.value)}>
                    <option value="">-- Select Exercise --</option>
                    {catalog.map(type => <option key={type.id} value={type.id}>{type.name}</option>)}
                </select>
                <button onClick={addExercise}>Add Exercise</button>

                {workout.exercises.map((ex, exIndex) => (
                    <div key={exIndex} style={{ border: '1px solid #ccc', padding: '10px', marginTop: '10px' }}>
                        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                            <strong>{ex.exerciseType.name}</strong>
                            <button onClick={() => removeExercise(exIndex)} style={{ color: 'red' }}>Remove Exercise</button>
                        </div>
                        
                        <table style={{ width: '100%', marginTop: '10px' }}>
                            <thead>
                                <tr>
                                    <th>Set</th>
                                    <th>Reps</th>
                                    {!ex.exerciseType.bodyweight && <th>Weight (kg)</th>}
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                {ex.sets.map((set, setIndex) => (
                                    <tr key={setIndex}>
                                        <td>{set.setNumber}</td>
                                        <td>
                                            <input type="number" value={set.reps} onChange={(e) => handleSetChange(exIndex, setIndex, 'reps', e.target.value)} />
                                        </td>
                                        {!ex.exerciseType.bodyweight && (
                                            <td>
                                                <input type="number" value={set.weight || ''} onChange={(e) => handleSetChange(exIndex, setIndex, 'weight', e.target.value)} />
                                            </td>
                                        )}
                                        <td><button onClick={() => {
                                            const updated = [...workout.exercises];
                                            updated[exIndex].sets.splice(setIndex, 1);
                                            setWorkout({ ...workout, exercises: updated });
                                        }}>Remove Set</button></td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                        <button onClick={() => addSet(exIndex)}>+ Add Set</button>
                    </div>
                ))}
            </div>

            <hr />
            <div style={{ marginTop: '20px', display: 'flex', gap: '10px' }}>
                <button onClick={saveWorkout} style={{ padding: '10px 20px', backgroundColor: '#3498db', color: 'white', border: 'none' }}>
                    SAVE WORKOUT
                </button>
                <button onClick={() => navigate('/dashboard')} style={{ padding: '10px 20px' }}>
                    Cancel
                </button>
            </div>
        </div>
    );
};

export default WorkoutEditor;
