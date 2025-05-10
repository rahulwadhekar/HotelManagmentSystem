import logo from './logo.svg';
import './App.css';
import Navbar from './Components/common/NavBar';
import { BrowserRouter,Routes,Route,Navigate } from 'react-router-dom';
import HomePage from './Components/home/HomePage';
import FooterComponent from './Components/common/Footer';

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Navbar />
        <div className="content">
          <Routes>
            {/* Public Routes */}
            <Route exact path="/home" element={<HomePage />} />
            {/* <Route exact path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/rooms" element={<AllRoomsPage />} />
            <Route path="/find-booking" element={<FindBookingPage />} /> */}

            {/* Protected Routes */}
            
          </Routes>
        </div>
        <FooterComponent />
      </div>
    </BrowserRouter>
  );
}

export default App;