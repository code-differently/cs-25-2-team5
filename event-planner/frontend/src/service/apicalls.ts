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