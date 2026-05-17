import React, { useState } from 'react';
import {
    Container,
    Box,
    Typography,
    TextField,
    Button,
    Card,
    CardContent,
    Link,
    Alert,
} from '@mui/material';
import axios from '../helper/axios';
import { useNavigate } from 'react-router-dom';

const Register: React.FC = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [error, setError] = useState<string | null>(null);
    const [fieldErrors, setFieldErrors] = useState<Record<string, string>>({});
    const [success, setSuccess] = useState(false);
    const navigate = useNavigate();

    const validate = () => {
        const errors: Record<string, string> = {};
        if (username.length < 3) errors.username = 'Username must be at least 3 characters';
        if (!email.includes('@')) errors.email = 'Invalid email address';
        if (password.length < 6) errors.password = 'Password must be at least 6 characters';
        if (password !== confirmPassword) errors.confirmPassword = 'Passwords do not match';
        
        setFieldErrors(errors);
        return Object.keys(errors).length === 0;
    };

    const handleRegister = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setFieldErrors({});

        if (!validate()) return;

        try {
            const response = await axios.post(`/api/auth/register`, {
                username,
                email,
                password
            });

            if (response.status === 201 || response.status === 200) {
                setSuccess(true);
                setTimeout(() => {
                    navigate('/login');
                }, 2000);
            }
        } catch (err: any) {
            console.error('Registration error:', err);
            if (typeof err.response?.data === 'object') {
                setFieldErrors(err.response.data);
            } else {
                setError(err.response?.data || 'Failed to register.');
            }
        }
    };

    return (
        <Container maxWidth="sm">
            <Box mt={10}>
                <Card elevation={4}>
                    <CardContent>
                        <Box p={2}>
                            <Typography variant="h4" align="center" gutterBottom color="primary" fontWeight="bold">
                                Create Account
                            </Typography>
                            <Typography variant="body1" align="center" color="textSecondary" mb={3}>
                                Join GymBuddy and start tracking your workouts today.
                            </Typography>

                            {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
                            {success && <Alert severity="success" sx={{ mb: 2 }}>Registration successful! Redirecting to login...</Alert>}

                            <form onSubmit={handleRegister}>
                                <TextField
                                    label="Username"
                                    variant="outlined"
                                    fullWidth
                                    margin="normal"
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                    error={!!fieldErrors.username}
                                    helperText={fieldErrors.username}
                                    required
                                />
                                <TextField
                                    label="Email Address"
                                    type="email"
                                    variant="outlined"
                                    fullWidth
                                    margin="normal"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    error={!!fieldErrors.email}
                                    helperText={fieldErrors.email}
                                    required
                                />
                                <TextField
                                    label="Password"
                                    type="password"
                                    variant="outlined"
                                    fullWidth
                                    margin="normal"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    error={!!fieldErrors.password}
                                    helperText={fieldErrors.password}
                                    required
                                />
                                <TextField
                                    label="Confirm Password"
                                    type="password"
                                    variant="outlined"
                                    fullWidth
                                    margin="normal"
                                    value={confirmPassword}
                                    onChange={(e) => setConfirmPassword(e.target.value)}
                                    error={!!fieldErrors.confirmPassword}
                                    helperText={fieldErrors.confirmPassword}
                                    required
                                />
                                <Button
                                    type="submit"
                                    variant="contained"
                                    color="primary"
                                    fullWidth
                                    size="large"
                                    sx={{ mt: 3, mb: 2, py: 1.5 }}
                                    disabled={success}
                                >
                                    Sign Up
                                </Button>
                                <Box textAlign="center">
                                    <Link 
                                        component="button" 
                                        variant="body2" 
                                        onClick={() => navigate('/login')}
                                    >
                                        Already have an account? Sign In
                                    </Link>
                                </Box>
                            </form>
                        </Box>
                    </CardContent>
                </Card>
            </Box>
        </Container>
    );
};

export default Register;
