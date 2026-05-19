import { Navigate } from "react-router-dom";
import { hasAuthenticate } from "../http";
import { useEffect, useState } from "react";
export default function PrivateRoute({children}){

    const [auth, setAuth] = useState(null);

    useEffect(() => {
        async function checkAuth(){
            const isAuth = await hasAuthenticate();
            setAuth(isAuth);
        }

        checkAuth();
    }, [])


    if (auth === null) {
        return <p>Carregando...</p>;
    }

    if (!auth) {
        window.location.href = "http://localhost:8080/auth";
        return null;
    }

    return children;

}