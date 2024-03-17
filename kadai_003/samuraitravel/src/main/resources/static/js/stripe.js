const stripe = Stripe('pk_test_51OlScLC7ku4vkHH6FxNqQVprxtBvDXSEuBgscp0sZmKkgglXHkouAtnXNjBWXLIJTQnQSsbCPRNXxQXk7DdFcrdi00RA7fKsL2');
const paymentButton = document.querySelector('#paymentButton');

paymentButton.addEventListener('click', () => {
	stripe.redirectToCheckout({
		sessionId: sessionId
	})
});