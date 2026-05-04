import { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Snackbar, Alert } from '@mui/material';
import { useWebSocket } from './hooks/useWebSocket';
import LandingPage from './features/LandingPage';
import Login from './features/Login';
import Register from './features/Register';
import ResetPassword from './features/ResetPassword';
import Dashboard from './features/Dashboard';
import WorkoutEditor from './features/WorkoutEditor';
import AdminUsers from './features/AdminUsers';
import AdminExercises from './features/AdminExercises';
import HealthDashboard from './features/HealthDashboard';
import ProtectedRoute from './components/ProtectedRoute';
import './App.css';

function App() {
  const { message, setMessage } = useWebSocket('/topic/socket/gym');
  const [open, setOpen] = useState(false);

  useEffect(() => {
    if (message) {
      setOpen(true);
    }
  }, [message]);

  const handleClose = (_event?: React.SyntheticEvent | Event, reason?: string) => {
    if (reason === 'clickaway') {
      return;
    }
    setOpen(false);
    setMessage(null);
  };

  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/reset-password" element={<ResetPassword />} />
        <Route 
          path="/dashboard" 
          element={
            <ProtectedRoute>
              <Dashboard />
            </ProtectedRoute>
          } 
        />
        <Route 
          path="/workout/new" 
          element={
            <ProtectedRoute>
              <WorkoutEditor />
            </ProtectedRoute>
          } 
        />
        <Route 
          path="/workout/:id" 
          element={
            <ProtectedRoute>
              <WorkoutEditor />
            </ProtectedRoute>
          } 
        />
        <Route 
          path="/health" 
          element={
            <ProtectedRoute>
              <HealthDashboard />
            </ProtectedRoute>
          } 
        />
        <Route 
          path="/admin-users" 
          element={
            <ProtectedRoute requiredRole="ADMINISTRATOR">
              <AdminUsers />
            </ProtectedRoute>
          } 
        />
        <Route 
          path="/admin-exercises" 
          element={
            <ProtectedRoute requiredRole="ADMINISTRATOR">
              <AdminExercises />
            </ProtectedRoute>
          } 
        />
      </Routes>
      <Snackbar 
        open={open} 
        autoHideDuration={6000} 
        onClose={handleClose} 
        anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
      >
        <Alert onClose={handleClose} severity="success" sx={{ width: '100%' }}>
          {message}
        </Alert>
      </Snackbar>
    </Router>
  );
}

export default App;
