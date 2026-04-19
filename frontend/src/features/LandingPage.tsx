import React from 'react';
import { Container, Box, Typography, Button, Paper, Stack } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import FitnessCenterIcon from '@mui/icons-material/FitnessCenter';

const LandingPage: React.FC = () => {
    const navigate = useNavigate();

    return (
        <Container maxWidth="md">
            <Box sx={{ mt: 15, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                <Paper elevation={3} sx={{ p: 6, textAlign: 'center', borderRadius: 4, width: '100%' }}>
                    <FitnessCenterIcon sx={{ fontSize: 80, color: 'primary.main', mb: 2 }} />
                    <Typography variant="h2" component="h1" gutterBottom fontWeight="bold">
                        GymBuddy
                    </Typography>
                    <Typography variant="h5" color="textSecondary" paragraph sx={{ mb: 4 }}>
                        Track your workouts, monitor your progress, and achieve your fitness goals.
                    </Typography>
                    
                    <Stack direction={{ xs: 'column', sm: 'row' }} spacing={3} justifyContent="center" sx={{ mt: 2 }}>
                        <Button 
                            variant="contained" 
                            size="large" 
                            onClick={() => navigate('/login')}
                            sx={{ px: 6, py: 1.5, fontSize: '1.1rem' }}
                        >
                            Login
                        </Button>
                        <Button 
                            variant="outlined" 
                            size="large" 
                            onClick={() => navigate('/register')}
                            sx={{ px: 6, py: 1.5, fontSize: '1.1rem' }}
                        >
                            Register
                        </Button>
                    </Stack>
                </Paper>
            </Box>
        </Container>
    );
};

export default LandingPage;
