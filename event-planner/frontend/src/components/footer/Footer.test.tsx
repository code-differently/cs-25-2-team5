import { render, screen } from "@testing-library/react";
import { MemoryRouter} from "react-router-dom";
import "@testing-library/jest-dom";
import Footer from "./Footer";

describe('Footer component', () => {
    it('renders footer text', () => {
        render(
            <MemoryRouter>
                <Footer />
            </MemoryRouter>
        );
        expect(screen.getByText('Code Differently')).toBeInTheDocument();
        expect(screen.getByText('Team 5')).toBeInTheDocument();
    });
});