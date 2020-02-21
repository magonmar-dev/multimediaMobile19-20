using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class GameController : MonoBehaviour
{
    private int points = 0;

    [SerializeField] GameObject flag;
    [SerializeField] Text scoreboardText;

    // Start is called before the first frame update
    void Start()
    {
        flag.SetActive(false);
        DisplayStatus();
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void LevelCompleted()
    {
        scoreboardText.text = "Level finished!";
        flag.SetActive(true);
    }

    public void PlayerHit()
    {
        SceneManager.LoadScene(0);
        CancelInvoke();
    }

    public void EnemyHit()
    {
        points += 100;
        DisplayStatus();
    }

    public void DisplayStatus()
    {
        scoreboardText.text = "MARIO\n" + points.ToString("D6");
    }
}
