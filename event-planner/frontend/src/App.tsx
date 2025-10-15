import './App.css'
import { Outlet } from 'react-router-dom';

function App() {
  return (
    <div className="app">
      {/* Navigation will go here when you're ready to add it */}
      <header className="app-header">
        <h1>Event Planner</h1>
      </header>
      
      <main className="app-main">
        <Outlet />
      </main>
      
      {/* Footer can go here if needed */}
    </div>
  );
}

export default App;
