const BASE_API_URL = import.meta.env.VITE_API_URL
export const fetchUser = async(clerkId: string)=> {
    try {
        const response = await fetch(`${BASE_API_URL}/users/clerk/${clerkId}`)
        if (response.ok) {
            const json = await response.json();
            return json;
            
            
        } else {
            console.error("Failed to fetch user", response.status);
        }
        } catch (err) {
            console.error("Error fetching user:", err);
        }
    }

export const fetchEventById = async(eventId: string) => {
    try {
        const response = await fetch(`${BASE_API_URL}/events/${eventId}`)
        if (response.ok) {
            const json = await response.json();
            return json;
            
            
        } else {
            console.error("Failed to fetch event", response.status);
        }
        } catch (err) {
            console.error("Error fetching event:", err);
        }
    }
export const fetchInvitedEvents = async (backendUser: any) => {

            try {
                const response = await fetch(`${BASE_API_URL}/users/${backendUser.id}/invites`);
                if (response.ok) {
                    const data = await response.json();
                    console.log("Fetched invited events:", data);
                    return data
                } else {
                    console.error("Failed to fetch invited events", response.status);
                }
            } catch (err) {
                console.error("Error fetching invited events:", err);
            }

};

export async function updateRsvpStatus(
  eventId: number,
  guestId: number,
  status: "accepted" | "declined"
): Promise<string> {
  try {
    const response = await fetch(
      `${BASE_API_URL}/event-guests/rsvp?eventId=${eventId}&guestId=${guestId}`,
      {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(status.toUpperCase()), // backend expects uppercase
      }
    );

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`RSVP update failed: ${response.status} - ${errorText}`);
    }

    // Backend returns the updated enum value (string)
    return await response.json();
  } catch (error) {
    console.error("Error updating RSVP:", error);
    throw error;
  }
}