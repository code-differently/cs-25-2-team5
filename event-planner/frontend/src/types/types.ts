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
        title: "hello",
        location: "Here",
        owner: "New",
        time: "1",
    },
    {
        id: "2",
        title: "hello2",
        location: "Here",
        owner: "New",
        time: "6",
    },
    {
        id: "3",
        title: "hello2",
        location: "Here",
        owner: "New",
        time: "7",
    },
    {
        id: "4",
        title: "hello3",
        location: "Here",
        owner: "New",
        time: "2",
    }
]