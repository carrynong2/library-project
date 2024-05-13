import ReactDOM from 'react-dom/client';
import './index.css';
import { App } from './App';
import { BrowserRouter } from "react-router-dom"
import { loadStripe } from '@stripe/stripe-js';
import { Elements } from '@stripe/react-stripe-js';

const stripePromise = loadStripe("pk_test_51PF5xWEtRJdXpNR4K9LBEx8nFw6JHTkuRlKYo4IVrfyGzk7lwQxjgbwDtjpqx0ypRGTq8EVmiPrGKqV4IVVvE97Y00udJ2TpR9");

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <BrowserRouter>
    <Elements stripe={stripePromise}>
      <App />
    </Elements>
  </BrowserRouter>
);
