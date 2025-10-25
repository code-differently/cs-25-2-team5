export interface Event {
    id: string;
    title: string;
    description: string;
    location: string;
    owner: string;
    time: string;
    imageUrl?: string
}

export const events: Event[] = [
    {
        id: "1",
        title: "Annual Tech Conference 2025",
        description: "Join industry leaders and innovators for a full day of cutting-edge technology presentations, networking opportunities, and hands-on workshops. Discover the latest trends in AI, cloud computing, and software development while connecting with like-minded professionals.",
        location: "San Francisco Convention Center",
        owner: "Sarah Johnson",
        time: "2025-11-15T09:00:00Z",
        imageUrl: "/tech-conference.jpg"
    },
    {
        id: "2",
        title: "Food Bank Volunteer Meetup",
        description: "Make a difference in your community by joining our monthly food bank volunteer drive. Help sort donations, pack food boxes, and distribute meals to families in need. No experience required - just bring your enthusiasm to help others!",
        location: "Central Park, Seattle WA",
        owner: "Mike Chen",
        time: "2025-10-25T14:00:00Z",
        imageUrl: "/foodbank.jpg"
    },
    {
        id: "3",
        title: "Children's Supplies Drive",
        description: "Help us collect essential supplies for local children in need. We're gathering school supplies, books, clothing, and toys to support underprivileged families. Every donation makes a meaningful impact in a child's life and education.",
        location: "Innovation Hub, Austin TX",
        owner: "Jennifer Martinez",
        time: "2025-11-02T18:30:00Z",
        imageUrl: "/children-supplies.jpg"
    },
    {
        id: "4",
        title: "Rainbow Run Festival",
        description: "Celebrate diversity and inclusion at our colorful 5K fun run! This family-friendly event features music, food trucks, face painting for kids, and prizes for creative costumes. All fitness levels welcome - walk, jog, or run to support equality and community unity.",
        location: "MIT Campus, Boston MA",
        owner: "Robert Kim",
        time: "2025-12-08T10:00:00Z",
        imageUrl: "/childrens-event.jpg"
    },
    {
        id: "5",
        title: "Moments in Nature",
        description: "Join us for a day of outdoor exploration and mindfulness. Connect with nature through guided hikes, meditation sessions, and creative workshops. All ages are welcome to participate in this rejuvenating experience.",
        location: "Greenwood Park, Seattle WA",
        owner: "Emily Davis",
        time: "2025-12-15T18:00:00Z",
        imageUrl: "/outdoor-event.jpg"
    },
    {
        id: "6",
        title: "Code Differently Capstone Presentation",
        description: "Capstone presentation for the Code Differently program showcasing our projects and learnings.",
        location: "Community Education Building, Wilmington DE",
        owner: "Team 5",
        time: "2025-10-27T17:00:00Z",
        imageUrl: "/teamwork.jpg"
    }
]

export type FormData = {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  confirmPassword: string;
};

export type FieldProps =  {
  label: string;
  id: string;
  name: keyof FormData ;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  error?: string;
  type?: string;
  autoComplete?: string;
  helpText?: string;
}
export interface PrivateEventDetails {
    title: string;
    date: string;
    time: string;
    location: string;
    description: string;
    rsvpStatus: "accepted" | "declined" | "pending";
}

export const invitedEvents: PrivateEventDetails[] = [
    {
        title: "Team Meeting",
        date: "2025-10-25",
        time: "14:00",
        location: "Conference Room A",
        description: "Monthly team sync-up meeting.",
        rsvpStatus: "pending"
    },
    {
        title: "Birthday Party",
        date: "2025-11-01",
        time: "18:00",
        location: "John's House",
        description: "John's 30th birthday celebration.",
        rsvpStatus: "accepted"
    },
    {
        title: "Workshop: React Basics",
        date: "2025-11-10",
        time: "10:00",
        location: "Online",
        description: "Introductory workshop for React beginners.",
        rsvpStatus: "declined"
    }
];
