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
        title: "Annual Tech Conference 2025",
        location: "San Francisco Convention Center",
        owner: "Sarah Johnson",
        time: "2025-11-15T09:00:00Z",
    },
    {
        id: "2",
        title: "React Workshop: Building Modern UIs",
        location: "Online via Zoom",
        owner: "Mike Chen",
        time: "2025-10-25T14:00:00Z",
    },
    {
        id: "3",
        title: "Startup Pitch Competition",
        location: "Innovation Hub, Austin TX",
        owner: "Jennifer Martinez",
        time: "2025-11-02T18:30:00Z",
    },
    {
        id: "4",
        title: "AI & Machine Learning Summit",
        location: "MIT Campus, Boston MA",
        owner: "Dr. Robert Kim",
        time: "2025-12-08T10:00:00Z",
    }
]