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

const ForgotPassword: React.FC = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [message, setMessage] = useState<string | null>(null);
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const handleReset = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);
        setMessage(null);

        try {
            const response = await axios.post(`/api/auth/forgot-password`, null, {
                params: {
                    username: username,
                    email: email,
                    newPassword: newPassword
                }
            });

            if (response.status === 200) {
                setMessage('Password reset successfully! You can now login.');
                setTimeout(() => {
                    navigate('/login');
                }, 3000);
            }
        } catch (err: any) {
            console.error('Reset error:', err);
            setError('Invalid username or email');
        }
    };

    return (
        <Container maxWidth="sm">
            <Box mt={10}>
                <Card elevation={4}>
                    <CardContent>
                        <Box p={2}>
                            <Typography variant="h4" align="center" gutterBottom color="primary" fontWeight="bold">
                                Reset Password
                            </Typography>
                            <Typography variant="body1" align="center" color="textSecondary" mb={3}>
                                Enter your details to reset your password.
                            </Typography>

                            {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
                            {message && <Alert severity="success" sx={{ mb: 2 }}>{message}</Alert>}

                            <form onSubmit={handleReset}>
                                <TextField
                                    label="Username"
                                    variant="outlined"
                                    fullWidth
                                    margin="normal"
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                    required
                                />
                                <TextField
                                    label="Email"
                                    type="email"
                                    variant="outlined"
                                    fullWidth
                                    margin="normal"
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                    required
                                />
                                <TextField
                                    label="New Password"
                                    type="password"
                                    variant="outlined"
                                    fullWidth
                                    margin="normal"
                                    value={newPassword}
                                    onChange={(e) => setNewPassword(e.target.value)}
                                    required
                                />
                                <Button
                                    type="submit"
                                    variant="contained"
                                    color="primary"
                                    fullWidth
                                    size="large"
                                    sx={{ mt: 3, mb: 2, py: 1.5 }}
                                >
                                    Reset Password
                                </Button>
                                <Box textAlign="center">
                                    <Link 
                                        component="button" 
                                        variant="body2" 
                                        onClick={() => navigate('/login')}
                                    >
                                        Back to Login
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

export default ForgotPassword;
