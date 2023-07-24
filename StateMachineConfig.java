@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
            .withStates()
                .initial(States.NEW)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
            .withExternal()
                .source(States.NEW).target(States.PACKAGED).event(Events.PACK)
                .and()
            .withExternal()
                .source(States.PACKAGED).target(States.PROCESSED).event(Events.PROCESS)
                .and()
            .withExternal()
                .source(States.PROCESSED).target(States.DELIVERED).event(Events.DELIVER)
                .and()
            .withExternal()
                .source(States.NEW).target(States.ERROR).event(Events.ERROR)
                .and()
            .withExternal()
                .source(States.PACKAGED).target(States.ERROR).event(Events.ERROR)
                .and()
            .withExternal()
                .source(States.PROCESSED).target(States.ERROR).event(Events.ERROR);
    }
}
