import { useEffect, useState } from 'react'
import '../App.css'
import { useNavigate } from 'react-router-dom';
import { Button, Box, Typography, Stack } from '@mui/material';
import axios from '../helper/axios';

interface ExerciseSet {
  id: number;
  setNumber: number;
  reps: number;
  weight: number | null;
}

interface ExerciseType {
  id: number;
  name: string;
  description: string;
}

interface Exercise {
  id: number;
  exerciseType: ExerciseType;
  sets: ExerciseSet[];
}

interface GymDay {
  id: number;
  name: string;
  date: string;
  notes: string;
  sleepQuality: number;
  energyLevel: number;
  exercises: Exercise[];
}

const Dashboard: React.FC = () => {
  const [workouts, setWorkouts] = useState<GymDay[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const userId = localStorage.getItem('USER_ID');
  const username = localStorage.getItem('USERNAME');
  const role = localStorage.getItem('ROLE');

  useEffect(() => {
    if (!userId) {
      navigate('/login');
      return;
    }

    axios.get(`/api/gymdays/user/${userId}`)
      .then(response => {
        setWorkouts(response.data);
        setLoading(false);
      })
      .catch(err => {
        console.error("Fetch Error:", err);
        setError("Backend is offline. Showing offline mock data...");
        // Fallback mock data
        setWorkouts([
          {
            id: 1,
            name: "Morning Blast",
            date: "2026-03-31",
            notes: "Felt very strong today.",
            sleepQuality: 8,
            energyLevel: 9,
            exercises: [
              {
                id: 11,
                exerciseType: { id: 101, name: "Bench Press", description: "Chest exercise" },
                sets: [
                  { id: 1001, setNumber: 1, reps: 10, weight: 60 },
                  { id: 1002, setNumber: 2, reps: 8, weight: 70 },
                  { id: 1003, setNumber: 3, reps: 6, weight: 80 }
                ]
              },
              {
                id: 12,
                exerciseType: { id: 102, name: "Pull Ups", description: "Back exercise" },
                sets: [
                  { id: 1004, setNumber: 1, reps: 12, weight: null },
                  { id: 1005, setNumber: 2, reps: 10, weight: null }
                ]
              }
            ]
          }
        ]);
        setLoading(false);
      });
  }, [userId, navigate]);

  const handleLogout = () => {
    localStorage.removeItem('USER_ID');
    localStorage.removeItem('USERNAME');
    localStorage.removeItem('ROLE');
    navigate('/login');
  };

  if (loading) return <div className="loading">Loading your progress...</div>;

  return (
    <div className="container">
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={4}>
        <Typography variant="h3">GymBuddy - Fitness Explorer</Typography>
        <Box display="flex" alignItems="center" gap={2}>
            <Typography variant="h6">Welcome, {username}!</Typography>
            <Stack direction="row" spacing={2}>
                <Button variant="contained" color="info" onClick={() => navigate('/health')}>Health Dashboard</Button>
                {role === 'ADMINISTRATOR' && (
                  <>
                    <Button variant="contained" color="secondary" onClick={() => navigate('/admin-users')}>User Management</Button>
                    <Button variant="contained" color="success" onClick={() => navigate('/admin-exercises')}>Exercise Catalog</Button>
                  </>
                )}
            </Stack>
            <Button variant="contained" color="primary" onClick={() => navigate('/workout/new')}>Log New Workout</Button>
            <Button variant="outlined" color="error" onClick={handleLogout}>Logout</Button>
        </Box>
      </Box>
      
      {error && <div style={{ textAlign: 'center', color: '#e74c3c', marginBottom: '20px' }}>{error}</div>}

      <div className="workouts-list">
        {workouts.map(workout => (
          <div key={workout.id} className="card">
            <div className="gym-day-header">
              <h2>{workout.name}</h2>
              <Box>
                <Button size="small" onClick={() => navigate(`/workout/${workout.id}`)}>Edit</Button>
                <Button size="small" color="error" onClick={async () => {
                  if (window.confirm("Delete this workout log?")) {
                    await axios.delete(`/api/gymdays/${workout.id}`);
                    window.location.reload();
                  }
                }}>Delete</Button>
              </Box>
              <span className="date">{new Date(workout.date).toLocaleDateString()}</span>
            </div>
            
            <div className="gym-day-info">
              <span>💤 Sleep: {workout.sleepQuality}/10</span>
              <span>⚡ Energy: {workout.energyLevel}/10</span>
            </div>

            <div className="exercises-list">
              {workout.exercises.map(ex => (
                <div key={ex.id} className="exercise-card">
                  <div className="exercise-name">{ex.exerciseType.name}</div>
                  <table className="sets-table">
                    <thead>
                      <tr>
                        <th>Set</th>
                        <th>Reps</th>
                        <th>Weight</th>
                      </tr>
                    </thead>
                    <tbody>
                      {ex.sets.map(set => (
                        <tr key={set.id}>
                          <td>{set.setNumber}</td>
                          <td>{set.reps}</td>
                          <td>{set.weight ? `${set.weight} kg` : '-'}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              ))}
            </div>

            {workout.notes && <div className="notes">" {workout.notes} "</div>}
          </div>
        ))}
      </div>
    </div>
  )
}

export default Dashboard;
