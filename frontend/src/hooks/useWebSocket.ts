import { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

export const useWebSocket = (topic: string) => {
    const [message, setMessage] = useState<string | null>(null);

    useEffect(() => {
        let stompClient: Stomp.Client | null = null;
        let isMounted = true;

        const connect = () => {
            try {
                const socket = new SockJS('http://localhost:8080/socket');
                stompClient = Stomp.over(socket);
                stompClient.debug = () => {};

                stompClient.connect({}, () => {
                    if (isMounted && stompClient) {
                        stompClient.subscribe(topic, (sdkEvent) => {
                            if (isMounted) setMessage(sdkEvent.body);
                        });
                    }
                }, (error) => {
                    if (isMounted) {
                        console.warn('WebSocket connection failed, will not retry automatically:', error);
                    }
                });
            } catch (err) {
                console.error('Failed to initialize WebSocket:', err);
            }
        };

        connect();

        return () => {
            isMounted = false;
            if (stompClient && stompClient.connected) {
                try {
                    stompClient.disconnect(() => {
                        console.log('Disconnected from WebSocket');
                    });
                } catch (e) {
                }
            }
        };
    }, [topic]);

    return { message, setMessage };
};
