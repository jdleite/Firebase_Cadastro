package com.cursoandroid.firebaseapp.firebasecadastro;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    private EditText email;
    private EditText senha;
    private Button botao;
    private RadioButton radio;
    private RadioGroup radioGroup;
    private TextView textoLogar;


    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.edtEmailId);

        textoLogar = (TextView) findViewById(R.id.txtLogId);

        radioGroup = (RadioGroup) findViewById(R.id.rdgId);

        senha = (EditText) findViewById(R.id.edtSenhaId);
        botao = (Button) findViewById(R.id.btnCadaId);





        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int pos = radioGroup.getCheckedRadioButtonId();

                radio = (RadioButton) findViewById(pos);


                botao.setText(radio.getText().toString());

            }
        });


        firebaseAuth = FirebaseAuth.getInstance();

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText().toString().equalsIgnoreCase("") || senha.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(),"Campo vazio",Toast.LENGTH_SHORT).show();
                }else{

                    if (radio.getText().toString().equalsIgnoreCase("Cadastrar")) {

                        cadastrar(email.getText().toString(), senha.getText().toString());


                    } else if (radio.getText().toString().equalsIgnoreCase("Logar")) {

                        logar(email.getText().toString(), senha.getText().toString());


                    } else if (radio.getText().toString().equalsIgnoreCase("Deslogar")) {

                        deslogar();

                    }

                }



            }
        });

        if( firebaseAuth.getCurrentUser() != null){

           textoLogar.setText(email.getText());

            Toast.makeText(getApplicationContext(),"Logado",Toast.LENGTH_SHORT).show();

        }else{
            Log.i("verifica","Usuário não está logado!!");
            Toast.makeText(getApplicationContext(),"Não Logado",Toast.LENGTH_SHORT).show();
        }

    }

    public void cadastrar(String email, String senha) {

        firebaseAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                try {

                    if (task.isSuccessful()) {

                        Toast.makeText(getApplicationContext(), "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                    } else {


                        Toast.makeText(getApplicationContext(), "Erro ao cadastrar", Toast.LENGTH_SHORT).show();

                    }


                } catch (Exception e) {

                    e.printStackTrace();

                }


            }


        });

    }

    public void logar(String email, String senha) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {


                        Toast.makeText(getApplicationContext(), "Logado", Toast.LENGTH_SHORT).show();


                    } else {


                        Toast.makeText(getApplicationContext(), "Erro ao Logar", Toast.LENGTH_SHORT).show();
                    }


                }


            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void deslogar() {

        try {
            firebaseAuth.signOut();
            if (firebaseAuth.getCurrentUser() != null) {

                Toast.makeText(getApplicationContext(), "Erro ao deslogar", Toast.LENGTH_SHORT).show();


            } else {


                Toast.makeText(getApplicationContext(), "Usuário deslogado", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
