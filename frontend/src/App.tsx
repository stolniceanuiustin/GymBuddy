import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './features/Login';
import ForgotPassword from './features/ForgotPassword';
import Dashboard from './features/Dashboard';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/" element={<Navigate to="/login" replace />} />
      </Routes>
    </Router>
  );
}

export default App;
