function A = LU(A, n)
% A = LU(A, n)
% Questo algoritmo restituisce nella matrice di ingresso la fattorizzazione
% della matrice stessa di dimensione n.
% A = matrice da fattorizzare e successivamente fattorizzata
% n = dimensione della matrice da fattorizzare

    p = [1 : n];
    for i = 1 : n - 1
        [mi, ki] = max(abs(A(i : n, i)));
        if (mi == 0)
            error("la matrice e' singolare");
        end
        ki = ki + i - 1;
        if (ki > i)
            A([i ki], :) = A([ki i], :);
            p([i ki]) = p([ki i]);
        end
        A(i + 1 : n, i) = A(i + 1 : n, i) / A(i, i);
        A(i + 1 : n, i + 1 : n) = A(i + 1 : n, i + 1 : n) - A(i + 1 : n, i) * A(i, i + 1 : n);
    end
end