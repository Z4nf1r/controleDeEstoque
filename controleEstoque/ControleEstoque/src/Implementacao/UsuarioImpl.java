/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Implementacao;

import Classes.Usuario;
import ConexaoDB.ConexaoDB;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aluno
 */
public class UsuarioImpl {

    public void validaLogon(Usuario usuario) {
        ConexaoDB conexao = new ConexaoDB();
        conexao.conectar();

        ResultSet rst = null;

        try {
            String sql = "select count(*) as existe from usuarios where login = ?";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            pst.setString(1, usuario.getLogin());
            rst = pst.executeQuery();

            while (rst.next()) {
                Integer aux = rst.getInt("existe");
               
                if(aux.equals(0)){
                    JOptionPane.showMessageDialog(null, "Usuário não existe!");
                    return;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar login!\nErro: " + ex.getMessage());
            return;
        }
       
        rst = null;

        try {
            String sql = "select count(*) as existe from usuarios where login = ? and senha = ?";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            pst.setString(1, usuario.getLogin());
            pst.setString(2, usuario.getSenha());
            rst = pst.executeQuery();

            while (rst.next()) {
                Integer aux = rst.getInt("existe");
               
                if(aux.equals(0)){
                    JOptionPane.showMessageDialog(null, "Senha Invalida!");
                    return;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar senha!\nErro: " + ex.getMessage());
            return;
        }

        rst = null;

        try {
            String sql = "select count(*) as existe from usuarios where login = ? and senha = ? and situacao = 'A' and (CURRENT_DATE between dataativacao and datadesativacao or datadesativacao is null)";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            pst.setString(1, usuario.getLogin());
            pst.setString(2, usuario.getSenha());
            rst = pst.executeQuery();

            while (rst.next()) {
                Integer aux = rst.getInt("existe");
               
                if(aux.equals(0)){
                    JOptionPane.showMessageDialog(null, "Usuario Sem Acesso!");
                    return;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar Acessos!\nErro: " + ex.getMessage());
            return;
        }
        conexao.desconectar();
    }
    
     public void popularTabela(JTable jTable1){
        ConexaoDB conexao = new ConexaoDB();
        conexao.conectar();
        ResultSet rst = null;
        
        try{
                String sql = "SELECT id_usuarios, nome, datanascimento, situacao, login, senha, '********' as senhaEscondida, dataativacao, datadesativacao FROM usuarios order by nome;";
                PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
                rst = pst.executeQuery();
                while(rst.next()){
                  DefaultTableModel tabelaFilme = (DefaultTableModel) jTable1.getModel();
                  
                  String id = rst.getString("id_usuarios");
                  String nome = rst.getString("nome");
                  Date datanascimento = rst.getDate("datanascimento");
                  String situacao = rst.getString("situacao");
                  String login = rst.getString("login");
                  String senha = rst.getString("senhaEscondida");
                  Date dataativacao = rst.getDate("dataativacao");
                  Date datadesativacao = rst.getDate("datadesativacao");
                  
                  Object[] obj = {id, nome, datanascimento, situacao, login, senha, dataativacao, datadesativacao};
                  
                  tabelaFilme.addRow(obj);
                          
                };
                
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Erro da mae do leo" + ex.getMessage());
        }
        conexao.desconectar();
    }
     
     public void pesquisarTabela(JTable jTable1, Usuario usuario) {
        ConexaoDB conexao = new ConexaoDB();
        conexao.conectar();

        while (jTable1.getModel().getRowCount() > 0) {
            ((DefaultTableModel) jTable1.getModel()).removeRow(0);
        }

        ResultSet rst = null;

        try {
            String sql = "SELECT * FROM usuarios WHERE UPPER(nome) like UPPER(?) ORDER BY nome";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            pst.setString(1, '%' + usuario.getNome() + '%');

            rst = pst.executeQuery();
            while (rst.next()) {
                DefaultTableModel tabelaUsuario = (DefaultTableModel) jTable1.getModel(); //pega modelo da tabela

                String idUsuario = rst.getString("idusuarios");
                String nome = rst.getString("nome");
                String dataNascimento = rst.getString("datanascimento");
                String situacao = rst.getString("situacao");
                String login = rst.getString("login");
                String senha = rst.getString("senha");
                String dataAtivacao = rst.getString("dataativacao");
                String dataDesativacao = rst.getString("datadesativacao");

                Object[] obj = {idUsuario, nome, dataNascimento, situacao, login, senha, dataAtivacao, dataDesativacao};

                tabelaUsuario.addRow(obj);
            };
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao popular tabela!\nErro: " + ex.getMessage());
        }

        conexao.desconectar();
    }
     
     public void inserirUsuario(Usuario usuario) {
        ConexaoDB conexao = new ConexaoDB();
        conexao.conectar();

        boolean LoginUsuarioExiste = false;
        ResultSet rst = null;

        //JOptionPane.showMessageDialog(null, " usuario.getNomeLogin() " + usuario.getNomeLogin());
        try {
            String sql = "SELECT COUNT(*) existe FROM usuarios WHERE login = ?";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            pst.setString(1, usuario.getLogin());

            rst = pst.executeQuery();

            while (rst.next()) {
                Integer aux = rst.getInt("existe");
                if (aux.equals(0)) {
                    LoginUsuarioExiste = false;
                } else {
                    LoginUsuarioExiste = true;
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar se o usuário existe no banco de dados INSERIR. " + ex.getMessage());
            return;
        }
        
         Integer idUsu = 0;
         try {
            String sql = "SELECT idusuario FROM usuarios WHERE idusuario = ?";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            pst.setInt(1, usuario.getId_usuarios());

            rst = pst.executeQuery();

            while (rst.next()) {
                Integer auxIdusuario = rst.getInt("idusuario");
                if (auxIdusuario.equals(0)) {
                    idUsu = auxIdusuario;
                } else {
                    idUsu = auxIdusuario;
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao consultar se o idUsuario. " + ex.getMessage());
            return;
        }

        if (LoginUsuarioExiste && (!usuario.getId_usuarios().equals(idUsu) && usuario.getId_usuarios() > 0)) {
            JOptionPane.showMessageDialog(null, "Este login já existe não pode ser inserido!");
            return;
        }

        if (LoginUsuarioExiste) {
            try {
                String sql = " UPDATE usuarios "
                        + "    SET    nome = ?, "
                        + "           datanascimento = ?, "
                        + "           login = ?, "
                        + "           senha = ? "
                        + "           situacao = ?, "
                        + "           dataativacao = ?, "
                        + "           datadesativacao = ? "
                        + " WHERE idusuario = ?";

                SimpleDateFormat formatoTexto = new SimpleDateFormat("yyyy-MM-dd");
                String dataAniversario = formatoTexto.format(usuario.getDatanascimento());
                String dataInicio = formatoTexto.format(usuario.getDataativacao());

                String dataFim;
                if (usuario.getDatadesativacao()== null) {
                    dataFim = "";
                } else {
                    dataFim = formatoTexto.format(usuario.getDatadesativacao());
                }

                java.sql.Date dataSQLAniver, dataSQLIni, dataSQLFim;
                dataSQLAniver = java.sql.Date.valueOf(dataAniversario);
                dataSQLIni = java.sql.Date.valueOf(dataInicio);

                if (dataFim.equals("")) {
                    dataSQLFim = null;
                } else {
                    dataSQLFim = java.sql.Date.valueOf(dataFim);
                }

                PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
                pst.setString(1, usuario.getNome());
                pst.setDate(2, dataSQLAniver);
                pst.setString(3, usuario.getLogin());
                pst.setString(4, usuario.getSenha());
                pst.setString(5, usuario.getSituacao());
                pst.setDate(6, dataSQLIni);
                pst.setDate(7, dataSQLFim);
                pst.setInt(8, usuario.getId_usuarios());

                pst.execute();

                JOptionPane.showMessageDialog(null, "Salvo com Sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao alterar os dados do usuario no banco de dados." + ex.getMessage());
            }
        } else {
            try {
                String sql = "INSERT INTO usuarios (nome, datanascimento, login, senha, situacao, dataativacao, datadesativacao) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);

                SimpleDateFormat formatoTexto = new SimpleDateFormat("yyyy-MM-dd");
                String dataAniversario = formatoTexto.format(usuario.getDatanascimento());
                String dataInicio = formatoTexto.format(usuario.getDataativacao());

                String dataFim;
                if (usuario.getDatadesativacao() == null) {
                    dataFim = "";
                } else {
                    dataFim = formatoTexto.format(usuario.getDatadesativacao());
                }

                java.sql.Date dataSQLAniver, dataSQLIni, dataSQLFim;
                dataSQLAniver = java.sql.Date.valueOf(dataAniversario);
                dataSQLIni = java.sql.Date.valueOf(dataInicio);

                if (dataFim.equals("")) {
                    dataSQLFim = null;
                } else {
                    dataSQLFim = java.sql.Date.valueOf(dataFim);
                }

                pst.setString(1, usuario.getNome());
                pst.setDate(2, dataSQLAniver);
                pst.setString(3, usuario.getLogin());
                pst.setString(4, usuario.getSenha());
                pst.setString(5, usuario.getSituacao());
                pst.setDate(6, dataSQLIni);
                pst.setDate(7, dataSQLFim);
                pst.execute();

                JOptionPane.showMessageDialog(null, "Salvo com Sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erro ao inserir os dados  do usuario no banco de dados." + ex.getMessage());
            }
        }
        conexao.desconectar();
    }
     
     public void pesquisarUsuario(Usuario usuario) {
        ConexaoDB conexao = new ConexaoDB();
        conexao.conectar();

        ResultSet rst = null;

        try {
            String sql = "SELECT * FROM usuarios WHERE idusuarios = ? ";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            pst.setInt(1, usuario.getId_usuarios());

            rst = pst.executeQuery();
            while (rst.next()) {
                usuario.setId_usuarios(rst.getInt("idusuarios"));
                usuario.setNome(rst.getString("nome"));
                usuario.setDatanascimento(rst.getDate("datanascimento"));
                usuario.setSituacao(rst.getString("situacao"));
                usuario.setLogin(rst.getString("login"));
                usuario.setSenha(rst.getString("senha"));
                usuario.setDataativacao(rst.getDate("dataativacao"));
                usuario.setDatadesativacao(rst.getDate("datadesativacao"));
            };
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisa usuário!\nErro: " + ex.getMessage());
        }

        conexao.desconectar();
    }
     
     public void excluirUsuario(Usuario usuario) {
        ConexaoDB conexao = new ConexaoDB();
        conexao.conectar();

        try {
            String sql = "DELETE FROM usuarios WHERE idusuarios = ? ";
            PreparedStatement pst = (PreparedStatement) conexao.conexao.prepareStatement(sql);
            pst.setInt(1, usuario.getId_usuarios());

            pst.execute();
            
            JOptionPane.showMessageDialog(null, "DELETADO com Sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao DELETAR usuário!\nErro: " + ex.getMessage());
        }

        conexao.desconectar();
    }

}
