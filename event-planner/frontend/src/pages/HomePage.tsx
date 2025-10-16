import React from 'react';
import EventList from '../components/event/EventList';

const HomePage: React.FC = () => {
  return (
    <section>
      <div style={{ 
        textAlign: 'center', 
        padding: '2rem 1rem',
        backgroundColor: '#f8f9fa'
      }}>
        <h1 style={{ 
          fontSize: '2.5rem', 
          marginBottom: '1rem',
          color: '#333'
        }}>
          Welcome to Event Planner
        </h1>
        <p style={{ 
          fontSize: '1.2rem',
          color: '#666',
          marginBottom: '2rem'
        }}>
          Discover and create amazing events in your community!
        </p>
      </div>
      
      <EventList />
    </section>
  );
};

export default HomePage;