@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private StateMachine<States, Events> stateMachine;

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        // Get the current state of the order from the database
        Order currentOrder = orderRepository.findById(id);

        // Send the appropriate event to the state machine based on the updated status of the order
        switch (order.getStatus()) {
            case "PACKAGED":
                stateMachine.sendEvent(Events.PACK);
                break;
            case "PROCESSED":
                stateMachine.sendEvent(Events.PROCESS);
                break;
            case "DELIVERED":
                stateMachine.sendEvent(Events.DELIVER);
                break;
            case "ERROR":
                stateMachine.sendEvent(Events.ERROR);
                break;
        }

        // Update the order in the database with the new status
        currentOrder.setStatus(order.getStatus());
        orderRepository.save(currentOrder);

        return ResponseEntity.ok(currentOrder);
    }
}
