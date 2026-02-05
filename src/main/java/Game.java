import ecs.EntityManager;
import ecs.EventManager;
import ecs.PrefabRegistry;
import ecs.components.*;
import ecs.events.*;
import ecs.systems.OutputSystem;
import ecs.systems.PrefabSystem;
import ecs.systems.ProducerSystem;

public class Game {

    private EntityManager em;
    private PrefabRegistry pf;
    private int frames;

    public Game() {
        pf = new PrefabRegistry();
        em = new EntityManager(pf);
        frames = 0;
        init();
    }

    public void update() {
        frames++;
        em.update(0);
    }

    private void init() {
        OutputSystem os = new OutputSystem();
        EventManager.subscribe("Print", e -> {
            os.printEntity(em.getEntity(((PrintEvent)e).entityId));
        });

        try {
            System.out.println("Loading Prefabs...");
            pf.loadPrefabs("file:src/main/java/ecs/Prefabs.json");
            System.out.println("Prefabs Loaded...");

            System.out.println("Creating initial Entities");
            em.createEntity(pf.get("factory"));
            em.createEntity(pf.get("hardware store"));
            System.out.println("Entities Loaded...");
        } catch(Exception e) {
            System.err.println(e);
        }

        System.out.println("Adding Systems...");
        em.addSystem(new ProducerSystem());

        System.out.println("Systems Added...");


//        em.addSystem(new PrefabSystem());
//        em.addSystem(new MovementSystem());

//        OutputSystem s = new OutputSystem();
//        em.addSystem(s);
    }
}
