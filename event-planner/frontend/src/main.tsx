import { createRoot } from 'react-dom/client';
import './index.css';
import App from './App.tsx';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Home from './pages/home/Home.tsx';
import Login from './pages/login/Login.tsx';
import CreateEvent from './pages/createEvent/CreateEvent.tsx';
import EventDetailsPage from './pages/events/EventDetails.tsx';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import React from 'react';
import SignUp from './pages/signup/SignUp.tsx';
import { ClerkProvider } from '@clerk/clerk-react';
import Invitations from './pages/invitations/Invitations.tsx';
import About from './pages/about/About.tsx';


const queryClient = new QueryClient();

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    children: [
      {
        path: '/',
        element: <Home />,
      },
      {
        path: '/about',
        element: <About />,
      },
      {
        path: '/invitations',
        element: <Invitations />,
      },
      {
        path: '/login',
        element: <Login />,
      },
      {
        path: '/signup',
        element: <SignUp />,
      },
      {
        path: '/createEvent',
        element: <CreateEvent />,
      },
      {
        path: '/event/:id',
        element: <EventDetailsPage />,
      },
    ],
  },
]);

const PUBLISHABLE_KEY = import.meta.env.VITE_CLERK_PUBLISHABLE_KEY
if (!PUBLISHABLE_KEY) {
  throw new Error('Missing Publishable Key')
}

createRoot(document.getElementById('root')!).render(

  <React.StrictMode>
    <ClerkProvider publishableKey={`${PUBLISHABLE_KEY}`} afterSignOutUrl="/">
      <QueryClientProvider client={queryClient}>
        <RouterProvider router={router} />
      </QueryClientProvider>
    </ClerkProvider>
  </React.StrictMode>,
);
