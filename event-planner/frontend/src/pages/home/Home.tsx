import React from 'react';
import EventList from '../../components/event/EventList';
import './Home.css';

const Home: React.FC = () => {
  return (
    <article>
      <section>
        <div>
            <h1 className="welcome-title">
              Welcome to Evynt
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