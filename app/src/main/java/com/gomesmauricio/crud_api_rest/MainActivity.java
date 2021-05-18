package com.gomesmauricio.crud_api_rest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText edtNome, edtId;
    TextView txtNome, txtId;
    Button btSalvar, btExluir, btBuscar;
    public static PessoaService pessoaService = null;

    private void carregarAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.1686.15.8:8096/api/va1")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MainActivity.pessoaService = retrofit.create(PessoaService.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNome = findViewById(R.id.edtNome);
        edtId   = findViewById(R.id.edtId);
        txtNome = findViewById(R.id.txtNome);
        txtId   = findViewById(R.id.txtId);
        btSalvar= findViewById(R.id.btSalvar);
        btExluir= findViewById(R.id.btExcluir);
        btBuscar= findViewById(R.id.btBuscar);

        carregarAPI();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void buscar(View v){
        int id = Integer.parseInt(edtId.getText().toString());
        Call<Pessoa> request = MainActivity.pessoaService.selectPessoa(id);
        Response<Pessoa> response = null;
        try {
            response = request.execute();
        }catch (IOException e){
            e.printStackTrace();
        }

        if (response !=null && response.isSuccessful() && response.body() !=null){
            Pessoa pessoa = new Pessoa(response.body().getID(), response.body().getNome());

            txtId.setText(String.valueOf(pessoa.getID()));
            txtNome.setText(pessoa.getNome());
            edtNome.setText(pessoa.getNome());
        }else {
            edtId.setText("");
            Toast.makeText(this, "Nenhuma pessoa encontrada com o id" + id, Toast.LENGTH_LONG).show();
        }
    }

    public void salvar(View v){
        String idStr = edtId.getText().toString();
        final  int id = idStr.equals("") ? 0 : Integer.parseInt(idStr);

        Pessoa p;
        Response response = null;
        Call<Pessoa> request = null;

        try {
            if(id > 0) {
                p = new Pessoa();
                p.setID(id);
                p.setNome(edtNome.getText().toString());
                //PUT na API enviando o id junto para esitar
                request = MainActivity.pessoaService.updatePessoa(id, p);
            }else{//novo cadastro
                //POST na API sem enviar o id que será gerado no banco
                p = new Pessoa();
                p.setNome(edtNome.getText().toString());
                request = MainActivity.pessoaService.insertPessoa(p);
            }
            response = request.execute();
        }catch (IOException e){
            e.printStackTrace();
        }

        if (!response.isSuccessful()){
            if (id > 0)
                Toast.makeText(this, "Não foi encontrado nenuma Paeesoa" +
                                                "com esse id para atualizar ou a "+
                                                "atualização não foi permitida. ",
                            Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "Não foi possivel "+
                                                "salvar essa Pessoa. ",
                                Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Pessoal salva com sucesso! ",
                            Toast.LENGTH_LONG).show();
            edtNome.setText("");
        }
    }

    public void excluir(View v){
        int cod = 0;
        Call<ResponseBody> response = null;

        try {
            //nonta a url para exclusão
            response = MainActivity.pessoaService.deletePessoa(Integer.parseInt
                                                                (edtId.getText().toString()));
            //executa e pega o codigo de retorno - 200 sucesso
            cod = response.execute().code();
        }catch (Exception e ){
            e.printStackTrace();
        }
        if (!response.isExecuted() && cod == 200){
            Toast.makeText(this, "Não foi encontrado nunhuma " +
                                                "Pessoa com esse id para excluir "+
                                                "ou a exclusão não foi permitida. ",
                                    Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Pessoa excluida com sucesso! ",
                                Toast.LENGTH_LONG).show();
        }

        if (!response.isExecuted() && cod == 200){
            Toast.makeText(this, "Não foi encontrado nenhuma " +
                                                "Pessoa com esse id para ecluir "+
                                            "ou a exclusão não foi permitida. ",
                                    Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Pessoa excluida com sucesso!",
                                        Toast.LENGTH_LONG).show();

            edtId.setText("");
            edtNome.setText("");
            txtId.setText("");
            txtNome.setText("");
        }
    }
}