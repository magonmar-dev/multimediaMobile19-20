using UnityEngine;

public class Enemy : MonoBehaviour
{
    private float enemySpeed = -2.5f;
    CircleCollider2D cc;
    BoxCollider2D bx;
    Rigidbody2D rb;
    Animator anim;
    GameController game;

    // Start is called before the first frame update
    void Start()
    {
        anim = gameObject.GetComponent<Animator>();
        bx = gameObject.GetComponent<BoxCollider2D>();
        cc = gameObject.GetComponent<CircleCollider2D>();
        rb = gameObject.GetComponent<Rigidbody2D>();
        game = FindObjectOfType<GameController>();
    }

    // Update is called once per frame
    void Update()
    {
        rb.velocity = new Vector2(enemySpeed, rb.velocity.y);
    }

    private void OnTriggerEnter2D(Collider2D other)
    {
        if(other.tag == "Pipe")
            enemySpeed = -enemySpeed;
    }

    private void OnCollisionEnter2D(Collision2D other)
    {
        if(other.gameObject.tag == "Player")
        {
            game.EnemyHit();
            anim.Play("EnemyDeath");
            enemySpeed = 0;
            Invoke("DestroEnemy", .5f);
            cc.enabled = false;
            bx.enabled = false;
            rb.gravityScale = 0;
            other.gameObject.GetComponent<Rigidbody2D>().AddForce(Vector2.up * 400);
        }
    }

    public void DestroEnemy()
    {
        Destroy(gameObject);
        CancelInvoke();
    }
}
