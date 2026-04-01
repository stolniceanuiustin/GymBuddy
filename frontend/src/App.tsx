import { useEffect, useState } from 'react'
import './App.css'

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

function App() {
  const [workouts, setWorkouts] = useState<GymDay[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  // In a real app, this would be the logged-in user's ID
  const CURRENT_USER_ID = 2;

  useEffect(() => {
    fetch(`http://localhost:8080/api/gymdays/user/${CURRENT_USER_ID}`)
      .then(res => {
        if (!res.ok) throw new Error("Could not fetch workouts");
        return res.json();
      })
      .then(data => {
        setWorkouts(data);
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
  }, []);

  if (loading) return <div className="loading">Loading your progress...</div>;

  return (
    <div className="container">
      <h1>GymBuddy - Fitness Explorer</h1>
      
      {error && <div style={{ textAlign: 'center', color: '#e74c3c', marginBottom: '20px' }}>{error}</div>}

      <div className="workouts-list">
        {workouts.map(workout => (
          <div key={workout.id} className="card">
            <div className="gym-day-header">
              <h2>{workout.name}</h2>
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

export default App
