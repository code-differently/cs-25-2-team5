import React from 'react';
import EventList from '../components/event/EventList';

const Home: React.FC = () => {
  return (
    <article>
      <section>
        <div style={{ 
          textAlign: 'center', 
          padding: '2rem 1rem',
          backgroundColor: '#f8f9fa'
        }}>
          <h1>
            Welcome to Event Planner
          </h1>
          <p>
            Discover and create amazing events in your community!
          </p>
        </div>
        
        <EventList />
      </section>
    </article>
  );
};

export default Home;