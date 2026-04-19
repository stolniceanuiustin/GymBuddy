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

const Login: React.FC = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState<string | null>(null);
    const navigate = useNavigate();

    const handleLogin = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);

        try {
            const response = await axios.post(`/api/auth/login`, null, {
                params: {
                    username: username,
                    password: password
                }
            });

            if (response.status === 200) {
                const user = response.data;
                localStorage.setItem('USER_ID', user.id.toString());
                localStorage.setItem('USERNAME', user.username);
                navigate('/dashboard');
            }
        } catch (err: any) {
            console.error('Login error:', err);
            setError('Invalid username or password');
        }
    };

    return (
        <Container maxWidth="sm">
            <Box mt={10}>
                <Card elevation={4}>
                    <CardContent>
                        <Box p={2}>
                            <Typography variant="h4" align="center" gutterBottom color="primary" fontWeight="bold">
                                GymBuddy Login
                            </Typography>
                            <Typography variant="body1" align="center" color="textSecondary" mb={3}>
                                Welcome back! Please enter your credentials.
                            </Typography>

                            {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

                            <form onSubmit={handleLogin}>
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
                                    label="Password"
                                    type="password"
                                    variant="outlined"
                                    fullWidth
                                    margin="normal"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
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
                                    Sign In
                                </Button>
                                <Box textAlign="center">
                                    <Link 
                                        component="button" 
                                        variant="body2" 
                                        onClick={() => navigate('/forgot-password')}
                                    >
                                        Forgot Password?
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

export default Login;