import './App.css'
import { Outlet } from 'react-router-dom';
import Navigation from './components/navigation/Navigation';
import { Footer } from './components/footer/Footer';


function App() {
  return (
    <>
      <Navigation />
      <div className="main">
        <div className="content">
          <Outlet />
        </div>
      </div>
      <Footer />
    </>
  );
}

export default App;
