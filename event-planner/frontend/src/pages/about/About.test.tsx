import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import AboutUs from './About';

describe('AboutUs Component', () => {
  beforeEach(() => {
    render(
      <MemoryRouter>
        <AboutUs />
      </MemoryRouter>
    );
  });
  it('renders the group photo', () => {
    const groupPhoto = screen.getByAltText('Our Team');
    expect(groupPhoto).toBeInTheDocument();
  });
  it('renders the team section header', () => {
  const teamSectionHeader = screen.getByText(/Our Team/i);
  expect(teamSectionHeader).toBeInTheDocument();
  });

  it('renders all team members', () => {
  expect(screen.getByText('Talia Crockett')).toBeInTheDocument();
  expect(screen.getByText('Daniel Boyce')).toBeInTheDocument();
  expect(screen.getByText('Ethan Hillman')).toBeInTheDocument();
  expect(screen.getByText('Calvin Robinson')).toBeInTheDocument();
  expect(screen.getByText('Joy Brown')).toBeInTheDocument();
  });
});
