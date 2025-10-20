import { render, screen } from '@testing-library/react';
import AboutUs from './About';
import '@testing-library/jest-dom';

describe('AboutUs Component', () => {
  beforeEach(() => {
    render(<AboutUs />);
  });

  test('renders the About Us header', () => {
    const headerElement = screen.getByText(/about us/i);
    expect(headerElement).toBeInTheDocument();
  });

  test('renders the Meet the Team section header', () => {
    const teamSectionHeader = screen.getByText(/meet the team!/i);
    expect(teamSectionHeader).toBeInTheDocument();
  });

  test('renders all team members', () => {
    // Check each team member's name is displayed
    const teamMembers = [
      'Talia Crockett',
      'Daniel Boyce', 
      'Ethan Hillman',
      'Calvin Robinson',
      'Joy Brown'
    ];
    
    teamMembers.forEach(name => {
      const nameElement = screen.getByText(name);
      expect(nameElement).toBeInTheDocument();
    });
  });

  test('renders all team member roles', () => {
    // Check each role is displayed
    const roles = ['Product Owner', 'Senior Developer', 'Junior Developer', 'Scrum Master'];
    
    roles.forEach(role => {
      // Using getAllByText since "Junior Developer" appears twice
      const roleElements = screen.getAllByText(role);
      expect(roleElements.length).toBeGreaterThan(0);
    });
  });

  test('renders correct number of team member cards', () => {
    // Find all User icons (one per team member)
    const userIconContainers = document.querySelectorAll('.aspect-square');
    expect(userIconContainers).toHaveLength(5);
  });

  test('displays correct team member and role pairings', () => {
    // Test specific member-role pairings
    const memberRolePairs = [
      { name: 'Talia Crockett', role: 'Product Owner' },
      { name: 'Joy Brown', role: 'Scrum Master' }
    ];

    memberRolePairs.forEach(({ name, role }) => {
      const nameElement = screen.getByText(name);
      // Find the parent container of the name element, then find the role within it
      const container = nameElement.closest('div').parentElement;
      const roleElement = container.querySelector('p');
      expect(roleElement.textContent).toBe(role);
    });
  });
});
