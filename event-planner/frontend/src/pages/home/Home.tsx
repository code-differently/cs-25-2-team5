import React from 'react';
import EventList from '../../components/event/EventList';
import './Home.css';
import { useUser } from '@clerk/clerk-react';
const Home: React.FC = () => {
   
  return (
    <article>
      <section>
        <div>
            <h1 className="welcome-title">
              Welcome to Eyvnt
              
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