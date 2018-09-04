function [A, p] = fatt_LUpivot(A)
    % [A, p] = fattorizzaLUpivot(A)
    % Calcola la fattorizzazione LU della matrice A.
    % A: matrice da fattorizzare.
    % Output:
    % A: la matrice fattorizzata LU;
    % p: vettore di permutazione
    [m,n]=size(A);
    if m~=n
        error('Matrice non quadrata');
    end
    p=(1:n);
    for i=1:n-1
        [mi, ki] = max(abs(A(i:n, i)));
        if mi==0
            error('Matrice singolare');
        end
        ki = ki+i-1;
        if ki>i
            A([i ki], :) = A([ki i], :);
            p([i ki]) = p([ki i]);
        end
        A(i+1:n, i) = A(i+1:n, i)/A(i, i);
        A(i+1:n, i+1:n) = A(i+1:n, i+1:n) -A(i+1:n, i)*A(i, i+1:n);
    end
end

function [b]= sistema_LUpivot(A,b,p)
    % [b]= sistema_LUpivot(A,b,p)
    % Calcola la soluzione di Ax=b con A matrice LU con pivoting parziale
    % A: Matrice matrice LU con pivoting (generata da fatt_LUpivot)
    % b: vettore colonna
    % Output:
    % b: soluzione del sistema
    P = zeros(length(A));
    for i = 1:length(A)
        P(i, p(i)) = 1;
    end
    b = sistema_triang_inf(tril(A,-1)+eye(length(A)), P*b);
    b = sistema_triang_sup(triu(A), b);
end