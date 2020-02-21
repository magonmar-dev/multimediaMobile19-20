using UnityEngine;

public class Player : MonoBehaviour
{
    float moveSpeed = 8f;
    float jumpSpeed = 12f;
    float playerHeight;
    bool jumping = false;

    Vector2 startPostion;
    BoxCollider2D bx;
    Rigidbody2D rb;
    Animator anim;
    GameController game;

    // Start is called before the first frame update
    void Start()
    {
        startPostion = transform.position;
        playerHeight = GetComponent<Collider2D>().bounds.size.y;
        anim = gameObject.GetComponent<Animator>();
        rb = gameObject.GetComponent<Rigidbody2D>();
        bx = gameObject.GetComponent<BoxCollider2D>();
        game = FindObjectOfType<GameController>();
    }

    // Update is called once per frame
    void Update()
    {
        float horizontal = Input.GetAxisRaw("Horizontal");

        if (horizontal > 0.1f && (!jumping))
        {
            transform.localScale =
                new Vector3(1, transform.localScale.y, transform.localScale.z);
            anim.Play("Walking");
        }
        else if (horizontal < -0.1f && (!jumping))
        {
            transform.localScale =
                new Vector3(-1, transform.localScale.y, transform.localScale.z);
            anim.Play("Walking");
        }

        if (Input.GetKeyDown(KeyCode.Space) && (!jumping))
        {
            float sideSpeed = 0;
            if (horizontal != 0)
                sideSpeed = horizontal;
            jumping = true;
            Vector3 jumpForce = new Vector3(sideSpeed * moveSpeed/2, jumpSpeed, 0);
            rb.AddForce(jumpForce, ForceMode2D.Impulse);
        }

        if (!jumping)
        {
            transform.Translate(horizontal * moveSpeed * Time.deltaTime, 0, 0);
        }
        else
        {
            transform.Translate(horizontal * moveSpeed/3 * Time.deltaTime, 0, 0);
            anim.Play("Jumping");
        }
    }

    private void OnTriggerEnter2D(Collider2D other)
    {
        if (other.tag == "LowerLimit")
            Reset();

        if (other.tag == "Pole")
            game.SendMessage("LevelCompleted");

        if(other.tag == "Enemy")
        {
            anim.Play("Death");
            moveSpeed = 0;
            jumpSpeed = 0;
            GetComponent<Player>().enabled = false;
            bx.enabled = false;
            rb.AddForce(Vector2.up * 500);
            rb.gravityScale = .8f;
            Invoke("Reset", 5f);
        }
    }

    private void OnCollisionEnter2D(Collision2D collision)
    {
        jumping = false;
    }

    private void Reset()
    {
        game.SendMessage("PlayerHit");
    }
}
