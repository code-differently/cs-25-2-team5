import EventForm from '../../components/eventForm/EventForm';
import './CreateEvent.css';
import { useUser } from '@clerk/clerk-react';
const CreateEvent = () => {
    const {isSignedIn,user} = useUser();
    console.log(user?.id);
    if(!isSignedIn) {
       window.location.href = "/";
    }
    

    return (
        <div className="create-event-page">
            <div className="event-image-section">
                <img
                    src="/childrens-drawing.jpg"
                    alt="Event Hero"
                    className="event-hero-image"
                />
                <div className="event-image-content">
                    <div className="event-image-text">
                        <h2>Create an Event</h2>
                        <p>Plan your next gathering with ease. Fill out the form to get started!</p>
                    </div>
                </div>
            </div>
            <div className="event-form-section">
                <div className="event-form-container">
                    <EventForm />
                </div>
            </div>
        </div>
    );
}

export default CreateEvent;
