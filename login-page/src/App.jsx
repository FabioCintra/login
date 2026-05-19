import {BrowserRouter, Routes, Route} from 'react-router-dom';
import PrivateRoute from './components/PrivateRoute';
import Login from "./pages/Login"
import StartRoute from './components/StartRoute';
import Callback from './components/Callback';
import Home from './pages/Home';

function App() {
 

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<StartRoute />}/> 
        <Route path="/login" element={<Login />}/> 
        <Route path="/authorized" element={<Callback />}/> 
        <Route path='/home' element={<PrivateRoute><Home /></PrivateRoute>} />
      </Routes>     
    </BrowserRouter>
  )
}

export default App
