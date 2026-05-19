export async function hasAuthenticate(){
    const response = await fetch("http://localhost:8080/auth/me", {
                method: "GET",
                credentials: "include"
            });
    
    return response.ok;
}