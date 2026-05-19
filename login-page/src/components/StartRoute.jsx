import { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import { hasAuthenticate } from "../http";

export default function StartRoute() {

    const [auth, setAuth] = useState(null);

    useEffect(() => {

        async function checkAuth() {
            const isAuth = await hasAuthenticate();

            if (!isAuth) {
                window.location.href = "http://localhost:8080/auth";
            } else {
                setAuth(true);
            }
        }

        checkAuth();

    }, []);

    if (auth === null) {
        return <p>Carregando...</p>;
    }
    

    return <Navigate to="/home" replace />;
}