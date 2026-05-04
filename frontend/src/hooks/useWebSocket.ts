import { useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

export const useWebSocket = (topic: string) => {
    const [message, setMessage] = useState<string | null>(null);

    useEffect(() => {
        const socket = new SockJS('http://localhost:8080/socket');
        const stompClient = Stomp.over(socket);
        // Disable debug logging to keep console clean
        stompClient.debug = () => {};

        stompClient.connect({}, () => {
            stompClient.subscribe(topic, (sdkEvent) => {
                setMessage(sdkEvent.body);
            });
        }, (error) => {
            console.error('WebSocket Error:', error);
        });

        return () => {
            if (stompClient) {
                stompClient.disconnect(() => {});
            }
        };
    }, [topic]);

    return { message, setMessage };
};
