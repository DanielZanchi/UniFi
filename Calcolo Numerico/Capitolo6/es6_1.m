function A = matrice_sparsa(n)
% function A = matrice_sparsa(n)
% Genera la matrice quadrata sparsa nxn con n maggiore di 10
% n numero di righe/colonne della matrice
% A matrice output
    if n <= 10
        error('n deve essere > 10')
    end

    ij = ones(n, 1) * 4;
    ij1 = ones(n, 1) * -1;
    ij10 = ij1;

    B = [ij ij1 ij1 ij10 ij10];
    d = [0, 1, -1, 10, -10];

    A = spdiags(B, d, n, n);
end
