export interface Event {
    id: string;
    title: string;
    location: string;
    owner: string;
    time: string;
}

export const events: Event[] = [
    {
        id: "1",
        title: "Team Meeting",
        location: "Conference Room A",
        owner: "Alice",
        time: "2024-06-10T09:00:00Z",
    },
    {
        id: "2",
        title: "Project Kickoff",
        location: "Conference Room B",
        owner: "Bob",
        time: "2024-06-11T13:30:00Z",
    },
    {
        id: "3",
        title: "Client Presentation",
        location: "Online",
        owner: "Carol",
        time: "2024-06-12T15:00:00Z",
    },
    {
        id: "4",
        title: "Weekly Sync",
        location: "Conference Room C",
        owner: "Dave",
        time: "2024-06-13T10:00:00Z",
    }
]