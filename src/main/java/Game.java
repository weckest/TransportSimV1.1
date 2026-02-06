import ecs.EntityManager;
import ecs.EventManager;
import ecs.PrefabRegistry;
import ecs.ProductTypeRegistry;
import ecs.components.*;
import ecs.events.*;
import ecs.systems.*;

public class Game {

    private EntityManager em;
    private PrefabRegistry pf;
    private ProductTypeRegistry ptr;
    private int frames;

    public Game() {
        pf = new PrefabRegistry();
        ptr = new ProductTypeRegistry();
        em = new EntityManager(pf, ptr);
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
            ptr.loadProducts("file:src/main/java/ecs/Products.json");
            System.out.println("Prefabs Loaded...");

            System.out.println("Creating initial Entities...");
            em.createEntity(pf.get("factory"));
            em.createEntity(pf.get("hardware store"));
            System.out.println("Entities Loaded...");
        } catch(Exception e) {
            System.err.println(e);
        }

        System.out.println("Adding Systems...");
        em.addSystem(new ProducerSystem());
        em.addSystem(new StockListSystem());

        //Event Systems
        em.addSystem(new BuyRequestSystem());
        em.addSystem(new SellRequestSystem());

        System.out.println("Systems Added...");

        System.out.println("Initialized...");
    }
}
