import { useRef } from 'react';
import { useNavigate } from 'react-router-dom';

import image from '../assets/image-login.png';

export default function Login(){
    const navigate = useNavigate();
    const getUserName = useRef();
    const getPassowrd = useRef();
    const inputStyle = "w-full px-2 py-2 outline focus:outline-purple-600 focus:outline-2";

    function handleRegisterScreen(){
        navigate("/register");
    }

    return(
        <div className="relative flex flex-col justify-center items-center h-screen bg-purple-900">
            <form
                action="http://localhost:8080/login"
                method="POST"
                className="relative w-[700px] h-[400px] flex flex-row border-b-black rounded-2xl shadow-lg shadow-black bg-white overflow-hidden"
            >
                <div className="flex-1 flex flex-col justify-center items-center">
                    <img src={image} alt="</> styled 1940s" />
                </div>

                <div className="relative w-[350px] flex flex-col justify-center px-10 space-y-5">
                    <input 
                        className={inputStyle}
                        type="text" 
                        name="username"
                        placeholder="User Name"
                        ref={getUserName}
                    />

                    <input 
                        className={inputStyle}
                        type="password" 
                        name="password"
                        placeholder="Password"
                        ref={getPassowrd}
                    />
                </div>
                
                <div className="absolute flex flex-row-reverse px-10 py-5 bottom-0 right-0 gap-5">
                    <button 
                        type="submit"
                        className="px-7 py-1 bg-purple-800 rounded-2xl font-roboto text-white hover:bg-purple-500"
                    >
                        Login
                    </button>
                </div>
            </form>
        </div>
    );
}