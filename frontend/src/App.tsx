import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import LandingPage from './features/LandingPage';
import Login from './features/Login';
import Register from './features/Register';
import ForgotPassword from './features/ForgotPassword';
import Dashboard from './features/Dashboard';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </Router>
  );
}

export default App;
