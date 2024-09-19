import React from 'react'
import Navbar from './pages/Navbar'
import ProfilePage from './pages/ProfilePage'
import { Route, Routes, BrowserRouter as Router } from 'react-router-dom'; 
import HomePage from './pages/HomePage';
const App = () => {
  return (
    <div >
      <Navbar/>
      <Routes>
      <Route path="/Profile" element={<ProfilePage/>} />
      <Route path="/homepage" element={<HomePage/>}/>
      </Routes>
    </div>
  )
}

export default App
