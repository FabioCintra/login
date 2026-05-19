import { useEffect } from "react";
import { useNavigate, Navigate } from "react-router-dom";
import { hasAuthenticate } from '../http.js';

export default function Callback(){
    const navigate = useNavigate();
    const params = new URLSearchParams(window.location.search);
    const code = params.get("code");

    useEffect(()=> {
        async function teste(){
            await fetch("http://localhost:8080/auth/callback", {
                method: 'POST',
                headers: {
                    "Content-Type": "application/json"
                },
                credentials: "include",
                body: JSON.stringify({code: code})
            })

            if(hasAuthenticate){
                navigate("/home");
            }
        }

        teste();
    },[])
    
}