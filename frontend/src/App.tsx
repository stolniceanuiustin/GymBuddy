import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LandingPage from './features/LandingPage';
import Login from './features/Login';
import Register from './features/Register';
import ForgotPassword from './features/ForgotPassword';
import Dashboard from './features/Dashboard';
import WorkoutEditor from './features/WorkoutEditor';
import AdminUsers from './features/AdminUsers';
import AdminExercises from './features/AdminExercises';
import HealthDashboard from './features/HealthDashboard';
import ProtectedRoute from './components/ProtectedRoute';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
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
    </Router>
  );
}

export default App;
