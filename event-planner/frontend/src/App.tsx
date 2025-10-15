import './App.css'
import { Outlet } from 'react-router-dom';
import Navbar from './components/Navbar';
import HomePage from './pages/HomePage';

function App() {
  return (
    <div className="app">
      <Navbar />
      <HomePage />
      <main className="app-main">
        <Outlet />
      </main>
      
      {/* Footer can go here if needed */}
    </div>
  );
}

export default App;
